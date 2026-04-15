<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useProyectosStore } from '@/stores/ProyectosStore'
import { ref, watch } from 'vue'



const empleadosProyecto = ref<any[]>([])

const cargarEmpleadosProyecto = async (idProyecto: number) => {

    const res = await fetch(
        `http://localhost:8080/empleado-proyecto/proyecto/${idProyecto}`
    )

    if (!res.ok) {
        empleadosProyecto.value = []
        return
    }

    empleadosProyecto.value = await res.json()
}

const route = useRoute()
const router = useRouter()
const store = useProyectosStore()

onMounted(async () => {
    if (!store.proyectos.length) {
        await store.fetchProyectos()
    }
})

const proyecto = computed(() =>
    store.proyectos.find(
        p => p.idProyecto === Number(route.params.id)
    )
)

const formatDate = (date: string | null) => {
    if (!date) return '—'
    return new Date(date).toLocaleDateString('es-ES')
}

watch(proyecto, (p) => {
    if (p?.idProyecto) {
        cargarEmpleadosProyecto(p.idProyecto)
    }
}, { immediate: true })
</script>

<template>

    <!-- BOTÓN BACK -->
    <div class="my-fixed">
        <v-btn variant="tonal" color="primary" @click="router.back()">
            <v-icon start>mdi-arrow-left</v-icon>
            Volver
        </v-btn>
    </div>

    <v-container v-if="proyecto">

        <v-card class="pa-6">

            <!-- HEADER -->
            <div class="d-flex justify-space-between align-center mb-4">

                <h2>{{ proyecto.descripcion }}</h2>

                <v-chip :color="proyecto.fechaBaja ? 'red' : 'green'" variant="flat">
                    {{ proyecto.fechaBaja ? 'BAJA' : 'ACTIVO' }}
                </v-chip>

            </div>

            <v-divider class="my-4" />

            <!-- INFO GENERAL -->
            <v-row>

                <v-col cols="12" md="2">
                    <p><b>ID:</b> {{ proyecto.idProyecto }}</p>
                </v-col>

                <v-col cols="12" md="6">
                    <p><b>Lugar:</b> {{ proyecto.lugar || '—' }}</p>
                </v-col>

                <v-col cols="12">
                    <p><b>Observaciones:</b> {{ proyecto.observaciones || '—' }}</p>
                </v-col>

            </v-row>

            <v-divider class="my-4" />

            <!-- FECHAS -->
            <v-row>

                <v-col cols="12" md="4">
                    <p><b>Fecha inicio:</b> {{ formatDate(proyecto.fechaInicio) }}</p>
                </v-col>

                <v-col cols="12" md="4">
                    <p><b>Fecha fin:</b> {{ formatDate(proyecto.fechaFin) }}</p>
                </v-col>

                <v-col cols="12" md="4">
                    <p><b>Fecha baja:</b> {{ formatDate(proyecto.fechaBaja) }}</p>
                </v-col>

            </v-row>
            <v-divider class="my-4" />

            <div class="mb-5">
                <b>Empleados asignados: </b>

                <span v-if="empleadosProyecto.length">
                    {{empleadosProyecto.map(e =>
                        `${e.nombre} ${e.apellido1}`
                    ).join(', ')}}
                </span>

                <span v-else class="text-grey">
                    No hay empleados asignados
                </span>
            </div>
            <v-row>
                <v-col cols="12" md="4"></v-col>
                <v-col cols="12" md="4">
                    <v-btn color="#7ab8ff" @click="router.push('/asignaciones')">
                        <v-icon start>mdi-account-plus</v-icon>
                        Asignar empleados
                    </v-btn>

                </v-col>
                <v-col cols="12" md="4"></v-col>

            </v-row>



        </v-card>

    </v-container>

    <!-- LOADING -->
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