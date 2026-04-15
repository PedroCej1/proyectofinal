<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const empleado = ref<any>(null)
const proyectosEmpleado = ref<any[]>([])

/* =========================
   CARGAR EMPLEADO
========================= */
const cargarEmpleado = async () => {

    const res = await fetch(
        `http://localhost:8080/empleados/${route.params.id}`
    )

    if (!res.ok) {
        empleado.value = null
        return
    }

    empleado.value = await res.json()
}

/* =========================
   PROYECTOS DEL EMPLEADO
========================= */
const cargarProyectos = async (idEmpleado: number) => {

    const res = await fetch(
        `http://localhost:8080/empleado-proyecto/empleado/${idEmpleado}`
    )

    if (!res.ok) {
        proyectosEmpleado.value = []
        return
    }

    proyectosEmpleado.value = await res.json()
}

/* =========================
   INIT
========================= */
onMounted(async () => {
    await cargarEmpleado()
})

/* =========================
   WATCH SIMPLE
========================= */
const cargarTodo = async () => {
    if (empleado.value?.idEmpleado) {
        await cargarProyectos(empleado.value.idEmpleado)
    }
}

/* cuando carga empleado -> cargar proyectos */
const waitForEmpleado = async () => {
    await cargarEmpleado()
    await cargarTodo()
}

onMounted(waitForEmpleado)

/* =========================
   FORMATO FECHA
========================= */
const formatDate = (date: string | null) => {
    if (!date) return '—'
    return new Date(date).toLocaleDateString('es-ES')
}
</script>

<template>

    <!-- BOTÓN BACK -->
    <div class="my-fixed">
        <v-btn variant="tonal" color="primary" @click="router.back()">
            <v-icon start>mdi-arrow-left</v-icon>
            Volver
        </v-btn>
    </div>

    <v-container v-if="empleado">

        <v-card class="pa-6">

            <!-- HEADER -->
            <div class="d-flex justify-space-between align-center mb-4">

                <h2>
                    {{ empleado.nombre }} {{ empleado.apellido1 }} {{ empleado.apellido2 || '' }}
                </h2>

                <v-chip :color="empleado.fechaBaja ? 'red' : 'green'" variant="flat">
                    {{ empleado.fechaBaja ? 'BAJA' : 'ACTIVO' }}
                </v-chip>

            </div>

            <v-divider class="my-4" />

            <!-- INFO -->
            <v-row>

                <v-col cols="12" md="3">
                    <p><b>ID:</b> {{ empleado.idEmpleado }}</p>
                </v-col>

                <v-col cols="12" md="3">
                    <p><b>NIF:</b> {{ empleado.nif }}</p>
                </v-col>

                <v-col cols="12" md="6">
                    <p><b>Email:</b> {{ empleado.email }}</p>
                </v-col>

            </v-row>

            <v-row>

                <v-col cols="12" md="4">
                    <p><b>Teléfono 1:</b> {{ empleado.telefono1 }}</p>
                </v-col>

                <v-col cols="12" md="4">
                    <p><b>Teléfono 2:</b> {{ empleado.telefono2 || '—' }}</p>
                </v-col>

                <v-col cols="12" md="4">
                    <p><b>Estado civil:</b> {{ empleado.estadoCivil }}</p>
                </v-col>

            </v-row>

            <v-divider class="my-4" />

            <!-- FECHAS -->
            <v-row>

                <v-col cols="12" md="4">
                    <p><b>Fecha nacimiento:</b> {{ formatDate(empleado.fechaNacimiento) }}</p>
                </v-col>

                <v-col cols="12" md="4">
                    <p><b>Fecha alta:</b> {{ formatDate(empleado.fechaAlta) }}</p>
                </v-col>

                <v-col cols="12" md="4">
                    <p><b>Fecha baja:</b> {{ formatDate(empleado.fechaBaja) }}</p>
                </v-col>

            </v-row>

            <v-divider class="my-4" />

            <!-- PROYECTOS -->
            <div>
                <b>Proyectos asignados: </b>

                <span v-if="proyectosEmpleado.length">
                    {{proyectosEmpleado.map(p =>
                        p.descripcion
                    ).join(', ')}}
                </span>

                <span v-else class="text-grey">
                    No tiene proyectos asignados
                </span>
            </div>

            <v-divider class="my-4" />

            <!-- BOTÓN -->
            <v-btn color="#7ab8ff" @click="router.push('/asignaciones')">
                <v-icon start>mdi-folder-account</v-icon>
                Asignar proyectos
            </v-btn>

        </v-card>

    </v-container>

    <v-container v-else class="text-center">
        <v-progress-circular indeterminate />
    </v-container>

</template>

<style>
.my-fixed {
    position: fixed;
    top: 80px;
    left: 20px;
    z-index: 1000;
}
</style>