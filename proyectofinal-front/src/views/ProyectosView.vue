<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useProyectosStore } from '@/stores/ProyectosStore'
import Swal from 'sweetalert2'
import { useRouter } from 'vue-router'

const router = useRouter()


const store = useProyectosStore()

const page = ref(1)
const itemsPerPage = ref(8)
const paginatedProyectos = computed(() => {
    const start = (page.value - 1) * itemsPerPage.value
    const end = start + itemsPerPage.value
    return proyectosFiltrados.value.slice(start, end)
})
const totalPages = computed(() =>
    Math.ceil(proyectosFiltrados.value.length / itemsPerPage.value)
)

const search = ref('')
const showBajas = ref(false)

onMounted(() => {
    store.fetchProyectos()
})

const formatDate = (date: string) => {
    if (!date) return ''
    return new Date(date).toLocaleDateString('es-ES')
}

const proyectosFiltrados = computed(() => {
    const base = store.proyectos.filter(p =>
        showBajas.value ? p.fechaBaja !== null : p.fechaBaja === null
    )

    if (!search.value) return base

    const q = search.value.toLowerCase()

    return base.filter(p =>
        p.descripcion?.toLowerCase().includes(q) ||
        p.idProyecto?.toString().includes(q)
    )
})

const confirmarBaja = async (id: number) => {

    const result = await Swal.fire({
        title: 'Dar de baja proyecto',
        text: '¿Seguro que quieres dar de baja este proyecto?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, dar de baja',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#d33'
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

const reactivar = async (id: number) => {
    const msg = await store.reactivarProyecto(id)

    if (msg) {
        Swal.fire({
            icon: 'success',
            title: 'Proyecto dado de alta',
            timer: 1200,
            showConfirmButton: false
        })
    }
}
</script>
<template>

    <v-container fluid>

        <!-- SEARCH + FILTRO -->
        <div class="d-flex ga-2 mb-4 flex-wrap">

            <v-text-field v-model="search" label="Buscar proyectos..." prepend-inner-icon="mdi-magnify"
                density="compact" variant="outlined" />

            <v-btn color="#7ab8ff" @click="router.push('/proyectos/nuevo')">
                <v-icon start>mdi-folder-plus</v-icon>
                Alta proyecto
            </v-btn>



        </div>

        <!-- GRID CARDS -->
        <v-row>

            <v-col v-for="p in paginatedProyectos" :key="p.idProyecto" cols="12" sm="6" md="4" lg="3">

                <v-card :style="{
                    backgroundImage: p.imagenUrl ? `linear-gradient(rgba(255,255,255,0.9), rgba(255,255,255,0.9)), url(${p.imagenUrl})` : '',
                    backgroundSize: 'cover',
                    backgroundPosition: 'center'
                }" class="project-card" elevation="3">


                    <v-card-title class="d-flex justify-space-between">


                        <span></span>


                        <v-chip size="small" :color="p.fechaBaja ? 'red' : 'green'" variant="flat">
                            {{ p.fechaBaja ? 'Baja' : 'Activo' }}
                        </v-chip>

                    </v-card-title>

                    <v-card-subtitle>
                        ID: {{ p.idProyecto }}
                    </v-card-subtitle>

                    <v-card-text>

                        <div class="text-body-2 mb-2">
                            <b> {{ p.descripcion }} </b>
                        </div>

                        <div class="text-caption">
                            <div>Inicio: {{ formatDate(p.fechaInicio) }}</div>
                            <div>Fin: {{ formatDate(p.fechaFin) }}</div>
                        </div>

                    </v-card-text>

                    <v-card-actions>

                        <v-btn size="small" color="primary" variant="tonal"
                            @click="router.push({ name: 'proyecto-detalle', params: { id: p.idProyecto } })">
                            Ver
                        </v-btn>

                        <v-spacer />

                        <v-btn v-if="!p.fechaBaja" icon="mdi-close" size="small" color="error"
                            @click="confirmarBaja(p.idProyecto)" />

                        <v-btn v-else icon="mdi-account-reactivate" size="small" color="success"
                            @click="reactivar(p.idProyecto)" />

                    </v-card-actions>

                </v-card>

            </v-col>


        </v-row>
        <div class="d-flex justify-center mt-6">

            <v-pagination v-model="page" :length="totalPages" rounded color="primary" />


        </div>
        <v-btn color="secondary" class="ou" @click="showBajas = !showBajas">
            <v-icon start>
                {{ showBajas ? 'mdi-eye' : 'mdi-eye-off' }}
            </v-icon>
            {{ showBajas ? 'Activos' : 'Bajas' }}
        </v-btn>
    </v-container>

</template>
<style>
.ou {
    margin: 10px;
    background-color: aliceblue;
    color: var(--primary);
}

.project-card {
    border-radius: 16px;
    transition: 0.2s ease;
}

.project-card:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.smart-container {
    width: 100%;
    margin: 0 auto;
}

.ultra-table {
    line-height: 1.1;
    border-radius: 20px;
}

.ultra-table :deep(td),
.ultra-table :deep(th) {
    white-space: nowrap;
    text-align: center;
}

.ultra-table :deep(td) {
    padding: 2px 6px !important;
}
</style>
