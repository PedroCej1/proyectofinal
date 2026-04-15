<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useProyectosStore } from '@/stores/ProyectosStore'
import Swal from 'sweetalert2'

const router = useRouter()
const store = useProyectosStore()

const error = ref('')

const menuInicio = ref(false)
const menuFin = ref(false)

const form = reactive({
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    lugar: '',
    observaciones: '',
    imagenUrl: ''
})

const submit = async () => {

    const values = Object.values(form)

    if (values.some(v => v === '')) {
        Swal.fire({
            icon: 'warning',
            title: 'Campos incompletos',
            text: 'Es obligatorio introducir todos los datos para dar de alta un nuevo proyecto',
        })
        return
    }

    const ok = await store.createProyecto(form)

    if (ok) {
        router.push('/proyectos')
    }
}

const cancel = () => {
    router.push('/proyectos')
}
</script>

<template>

    <div class="my-fixed">
        <v-btn variant="tonal" class="ma-5" color="primary" @click="router.back()">
            <v-icon start>mdi-arrow-left</v-icon>
            Volver
        </v-btn>
    </div>

    <v-container class="form-container">


        <v-card class="pa-6" elevation="3">

            <h2 class="mb-4">Alta proyecto</h2>

            <v-alert v-if="error" type="error" class="mb-4">
                {{ error }}
            </v-alert>

            <v-row>

                <!-- Descripción -->
                <v-col cols="12">
                    <v-text-field v-model="form.descripcion" placeholder="Descripción del proyecto" />
                </v-col>

                <!-- Lugar -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.lugar" placeholder="Lugar" />
                </v-col>

                <!-- Imagen URL -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.imagenUrl" placeholder="URL de imagen (opcional o de internet)" />
                </v-col>

                <!-- Fecha inicio -->
                <v-col cols="12" md="6">
                    <v-menu v-model="menuInicio" :close-on-content-click="false" transition="scale-transition" offset-y>

                        <template v-slot:activator="{ props }">
                            <v-text-field v-model="form.fechaInicio" placeholder="Fecha inicio" readonly v-bind="props"
                                prepend-inner-icon="mdi-calendar" />
                        </template>

                        <v-date-picker v-model="form.fechaInicio" @update:model-value="menuInicio = false" />

                    </v-menu>
                </v-col>

                <!-- Fecha fin -->
                <v-col cols="12" md="6">
                    <v-menu v-model="menuFin" :close-on-content-click="false" transition="scale-transition" offset-y>

                        <template v-slot:activator="{ props }">
                            <v-text-field v-model="form.fechaFin" placeholder="Fecha fin" readonly v-bind="props"
                                prepend-inner-icon="mdi-calendar" />
                        </template>

                        <v-date-picker v-model="form.fechaFin" @update:model-value="menuFin = false" />

                    </v-menu>
                </v-col>

                <!-- Observaciones -->
                <v-col cols="12">
                    <v-textarea v-model="form.observaciones" placeholder="Observaciones" rows="3" />
                </v-col>

            </v-row>

            <div class="d-flex justify-end ga-2 mt-4">

                <v-btn color="primary" @click="submit">
                    <v-icon start>mdi-content-save</v-icon>
                    Guardar
                </v-btn>

                <v-btn variant="tonal" @click="cancel">
                    <v-icon start>mdi-close</v-icon>
                    Cancelar
                </v-btn>

            </div>

        </v-card>

    </v-container>
</template>

<style scoped>
.form-container {
    max-width: 900px;
    margin: auto;
}

.my-fixed {
    position: fixed;
    top: 70px;
    left: 20px;
}
</style>