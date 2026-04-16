<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useEmpleadosStore } from '@/stores/EmpleadosStore'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

const router = useRouter()
const store = useEmpleadosStore()

const showAll = ref(false)
const selected = ref<any[]>([])
const showBajas = ref(false)
const search = ref('')
const empleadosFiltrados = computed(() => {
    const data = store.empleados

    const base = data.filter(e =>
        showBajas.value
            ? e.fechaBaja !== null
            : e.fechaBaja === null
    )

    if (!search.value) return base

    const q = search.value.toLowerCase()

    return base.filter(e =>
        e.idEmpleado?.toString().includes(q) ||
        e.nif?.toLowerCase().includes(q) ||
        e.nombre?.toLowerCase().includes(q) ||
        `${e.apellido1} ${e.apellido2}`.toLowerCase().includes(q)
    )
})


const formatDate = (date: string) => {
    if (!date) return ''
    return new Date(date).toLocaleDateString('es-ES')
}

const confirmarBaja = async (id: number) => {

    const result = await Swal.fire({
        title: '¿Cambiar estado de baja del empleado?',
        text: 'Esta acción cambiará la fecha de alta o baja permanentemente',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
    })

    if (!result.isConfirmed) return

    const ok = await store.darDeBaja(id)

    if (ok) {
        Swal.fire({
            icon: 'success',
            title: 'Proyecto dado de baja',
            timer: 1200,
            showConfirmButton: false
        })
    }

}


onMounted(() => {
    store.fetchEmpleados()
})

const headersBasic = [
    { title: 'Nombre', key: 'nombre' },
    { title: 'Apellidos', key: 'apellidos' },
    { title: 'Teléfono', key: 'telefono1' },
    { title: 'Email', key: 'email' },
    { title: 'Acciones', key: 'actions', sortable: false },
]

const headersFull = [
    { title: 'ID', key: 'idEmpleado' },
    { title: 'NIF', key: 'nif' },
    { title: 'Nombre', key: 'nombre' },
    { title: 'Apellidos', key: 'apellidos' },
    { title: 'Nacimiento', key: 'fechaNacimiento' },
    { title: 'Teléfono', key: 'telefono1' },
    { title: 'Teléfono2', key: 'telefono2' },
    { title: 'Email', key: 'email' },
    { title: 'Alta', key: 'fechaAlta' },
    { title: 'Baja', key: 'fechaBaja' },
    { title: 'Estado Civil', key: 'estadoCivil' },
    { title: 'Formación', key: 'formacionUniversitaria' },
    { title: 'Acciones', key: 'actions', sortable: false },
]

const headers = computed(() =>
    showAll.value ? headersFull : headersBasic
)

// ==========================
// ACCIONES MASIVAS
// ==========================

const darBajaSeleccionados = async () => {
    for (const emp of selected.value) {
        await store.darDeBaja(emp.idEmpleado)
    }
}

const reactivarSeleccionados = async () => {
    for (const emp of selected.value) {
        if (emp.fechaBaja) {
            await store.reactivarEmpleado(emp.idEmpleado)
        }
    }
    selected.value = []
}

</script>

<template>

    <v-container fluid class="smart-container">


        <v-text-field v-model="search" label="Buscar empleados por Nombre, ID, NIF..." variant="outlined"
            density="compact" />


        <!-- BOTONES SUPERIORES -->
        <div class="mb-4 d-flex ga-2 flex-wrap">

            <v-btn color="primary" class="mio" @click="showAll = !showAll">
                <v-icon start>
                    {{ showAll ? 'mdi-minus' : 'mdi-plus' }}
                </v-icon>

                {{ showAll ? 'Vista simple' : 'Ver todos los datos' }}
            </v-btn>
            <v-btn color="#7ab8ff" @click="router.push('/empleados/nuevo')">
                <v-icon start>mdi-account-plus</v-icon>
                Alta empleado
            </v-btn>

            <v-spacer></v-spacer>

            <!-- ACCIONES MASIVAS -->
            <v-btn color="error" :disabled="!selected.length" @click="darBajaSeleccionados">
                <v-icon start>mdi-close</v-icon>
                Dar de baja seleccionados
            </v-btn>

            <v-btn color="success" :disabled="!selected.length" @click="reactivarSeleccionados">
                <v-icon start>mdi-account-reactivate</v-icon>
                Reactivar seleccionados
            </v-btn>

        </div>

        <!-- TABLA -->
        <v-data-table @click:row="(event, item) => router.push(`/empleados/${item.item.idEmpleado}`)" return-object
            v-model="selected" :headers="headers" :items="empleadosFiltrados" striped="even"
            :no-data-text="'No se ha encontrado ningún Empleado'" :items-per-page-text="'Elementos por página'"
            :loading="store.loading" item-value="idEmpleado" show-select density="compact" class="ultra-table">


            <!-- APPELLIDOS -->
            <template #item.apellidos="{ item }">
                {{ item.apellido1 }} {{ item.apellido2 }}
            </template>

            <template #item.fechaNacimiento="{ item }">
                {{ formatDate(item.fechaNacimiento) }}
            </template>
            <template #item.fechaAlta="{ item }">
                {{ formatDate(item.fechaAlta) }}
            </template>
            <template #item.fechaBaja="{ item }">
                {{ formatDate(item.fechaBaja) }}
            </template>

            <!-- ACCIONES INDIVIDUALES -->
            <template #item.actions="{ item }">
                <div @click.stop>
                    <v-btn v-if="!item.fechaBaja" icon="mdi-close" class="mini-btn" color="error" size="x-small"
                        @click="confirmarBaja(item.idEmpleado)" />

                    <v-btn v-else icon="mdi-account-reactivate" class="mini-btn" color="success" size="x-small"
                        @click="confirmarBaja(item.idEmpleado)" />
                </div>



            </template>

        </v-data-table>
        <v-btn color="secondary" class="ou" @click="showBajas = !showBajas">
            <v-icon start>
                {{ showBajas ? 'mdi-account' : 'mdi-account-off' }}
            </v-icon>

            {{ showBajas ? 'Ver activos' : 'Ver usuarios de baja' }}
        </v-btn>

    </v-container>

</template>

<style>
.ou {
    margin: 10px;
    background-color: aliceblue;
    color: var(--primary);
}

.smart-container {

    width: 100%;
    margin: 0 auto;
}

.ultra-table {
    line-height: 1.1;
    border-radius: 20px;

}

/* HEADER */
.ultra-table :deep(thead) {
    padding: 2px 6px !important;
    height: 26px !important;
    white-space: nowrap;
}



.ultra-table :deep(th) {
    white-space: nowrap;
}

/* CELDAS */
.ultra-table :deep(td) {
    padding: 2px 6px !important;
    height: 15px !important;
    white-space: nowrap;
    text-align: center;

}

/* FILAS */
.ultra-table :deep(tr) {
    height: 15px !important;
}

/* CHECKBOX COLUMN */
.ultra-table :deep(.v-selection-control) {
    transform: scale(0.75);
    /* 👈 checkbox más pequeño */
}

/* BOTONES ACCIONES */
.ultra-table :deep(.v-btn) {
    min-width: 15px !important;
    width: 24px !important;
    height: 24px !important;
    padding: 0 !important;
}

/* ICONOS */

.mini-btn {
    width: 20px;
    height: 20px;
    min-width: 14px;
    padding: 0 !important;
    z-index: 1000;
}

.link {
    cursor: pointer;
}

.link:hover {
    text-decoration: underline;
}
</style>