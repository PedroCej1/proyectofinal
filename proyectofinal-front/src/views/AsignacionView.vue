<script setup lang="ts">
import { onMounted, ref, watch, computed } from 'vue'
import Swal from 'sweetalert2'

/* =========================
   PROYECTOS + EMPLEADOS
========================= */
const proyectos = ref<any[]>([])
const empleados = ref<any[]>([])

const proyectoSeleccionado = ref<number | null>(null)
const empleadosSeleccionados = ref<number[]>([])

/* =========================
   BUSCADOR EMPLEADOS
========================= */
const search = ref('')

/* =========================
   LOAD INITIAL DATA
========================= */
onMounted(async () => {

    const [resP, resE] = await Promise.all([
        fetch('http://localhost:8080/proyectos'),
        fetch('http://localhost:8080/empleados')
    ])

    const dataP = await resP.json()
    const dataE = await resE.json()

    proyectos.value = dataP.filter((p: any) => !p.fechaBaja)
    empleados.value = dataE.filter((e: any) => !e.fechaBaja)
})

/* =========================
   CUANDO CAMBIA PROYECTO
   -> cargar asignados
========================= */
watch(proyectoSeleccionado, async (id) => {

    if (!id) return

    const res = await fetch(
        `http://localhost:8080/empleado-proyecto/proyecto/${id}`
    )

    const data = await res.json()

    const ids = data.map((e: any) => e.idEmpleado)

    empleadosSeleccionados.value = [...ids]
    empleadosOriginales.value = [...ids]   //  guardar original
})

/* =========================
   TOGGLE ASIGNACIÓN
========================= */
const toggleEmpleado = async (idEmpleado: number) => {

    const idProyecto = proyectoSeleccionado.value
    if (!idProyecto) return

    const index = empleadosSeleccionados.value.indexOf(idEmpleado)

    if (index !== -1) {

        empleadosSeleccionados.value.splice(index, 1)

        await fetch(
            `http://localhost:8080/empleado-proyecto/${idEmpleado}/${idProyecto}`,
            { method: 'DELETE' }
        )

    } else {

        empleadosSeleccionados.value.push(idEmpleado)

        await fetch(
            `http://localhost:8080/empleado-proyecto/${idEmpleado}/${idProyecto}`,
            { method: 'POST' }
        )
    }
}

/* =========================
   FILTRO BUSCADOR EMPLEADOS
========================= */
const empleadosFiltrados = computed(() => {

    if (!search.value) return empleados.value

    const q = search.value.toLowerCase()

    return empleados.value.filter(e =>
        `${e.nombre} ${e.apellido1} ${e.apellido2}`
            .toLowerCase()
            .includes(q)
    )
})

/* =========================
   ASIGNACIÓN MASIVA (opcional)
========================= */
const asignar = async () => {

    if (!proyectoSeleccionado.value) {
        Swal.fire({
            icon: 'warning',
            title: 'Selecciona un proyecto'
        })
        return
    }



    Swal.fire({
        icon: 'success',
        title: 'Cambios guardados',
        timer: 1200,
        showConfirmButton: false
    })

}

const empleadosOrdenados = computed(() => {

    const lista = empleadosFiltrados.value

    return [...lista].sort((a, b) => {

        const aSelected = empleadosSeleccionados.value.includes(a.idEmpleado)
        const bSelected = empleadosSeleccionados.value.includes(b.idEmpleado)

        return Number(bSelected) - Number(aSelected)
    })
})

const page = ref(1)
const itemsPerPage = 30

const empleadosPaginados = computed(() => {
    const start = (page.value - 1) * itemsPerPage
    const end = start + itemsPerPage
    return empleadosOrdenados.value.slice(start, end)
})

const totalPages = computed(() =>
    Math.ceil(empleadosOrdenados.value.length / itemsPerPage)
)

const empleadosOriginales = ref<number[]>([])
const hayCambios = computed(() => {
    return JSON.stringify(empleadosOriginales.value.sort()) !==
        JSON.stringify(empleadosSeleccionados.value.sort())
})
</script>

<template>

    <v-container>

        <h2 class="mb-4 ma-0">Selecciona un Proyecto para comenzar a asignar Empleados</h2>




        <v-container fluid class="smart-container">
            <!-- =========================
         SELECT PROYECTOS (CON BUSCADOR)
    ========================== -->
            <v-autocomplete v-model="proyectoSeleccionado" :items="proyectos" item-title="descripcion"
                item-value="idProyecto" label="Selecciona proyecto" variant="outlined" clearable
                density="comfortable" />

            <!-- BUSCADOR -->
            <v-text-field v-model="search" label="Buscar empleados..." variant="outlined" density="compact" />

            <!-- TABLA -->
            <v-container fluid>
                <!-- BOTÓN -->
                <v-btn v-if="hayCambios" class="mb-4" color="primary" @click="asignar">
                    Guardar cambios
                </v-btn>


                <!-- GRID -->
                <v-row dense>

                    <v-col v-for="emp in empleadosPaginados" :key="emp.idEmpleado" cols="12" sm="6" md="4">

                        <v-card class="pa-2 empleado-card"
                            :elevation="empleadosSeleccionados.includes(emp.idEmpleado) ? 4 : 1"
                            @click="toggleEmpleado(emp.idEmpleado)">

                            <div class="d-flex align-center">

                                <v-checkbox :model-value="empleadosSeleccionados.includes(emp.idEmpleado)"
                                    density="compact" hide-details />

                                <span class="ml-2">
                                    {{ emp.nombre }} {{ emp.apellido1 }} {{ emp.apellido2 }}
                                </span>

                            </div>

                        </v-card>

                    </v-col>

                </v-row>
                <div class="d-flex justify-center mt-4">
                    <v-pagination v-model="page" :length="totalPages" rounded color="primary" />
                </div>

            </v-container>

        </v-container>



    </v-container>

</template>

<style>
.empleado-card {
    cursor: pointer;
    transition: 0.15s ease;
}

.empleado-card:hover {
    transform: translateY(-2px);
}
</style>