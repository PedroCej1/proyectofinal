<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useEmpleadosStore } from '@/stores/EmpleadosStore'
import Swal from 'sweetalert2'

const router = useRouter()
const store = useEmpleadosStore()

const error = ref('')
const menuNacimiento = ref(false)
const menuAlta = ref(false)

const form = reactive({
    nif: '',
    nombre: '',
    apellido1: '',
    apellido2: '',
    fechaNacimiento: '',
    fechaAlta: '',
    telefono1: '',
    telefono2: '',
    email: '',
    estadoCivil: '',
    formacionUniversitaria: '',
})

const submit = async () => {
    const values = Object.values(form)

    if (values.some(v => !v)) {
        Swal.fire({
            icon: 'warning',
            title: 'Campos incompletos',
            text: 'Es obligatorio introducir todos los datos para dar de alta un nuevo empleado',
        })
        return
    }

    const ok = await store.createEmpleado(form)

    if (ok) {
        router.push('/empleados')
    }
}

const cancel = () => {
    router.push('/empleados')
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

            <h2 class="mb-4">Alta empleado</h2>

            <v-alert v-if="error" type="error" class="mb-4">
                {{ error }}
            </v-alert>

            <v-row>

                <!-- Nombre -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.nombre" placeholder="Nombre" />
                </v-col>

                <!-- NIF -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.nif" placeholder="NIF" />
                </v-col>

                <!-- Apellido 1 -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.apellido1" placeholder="Primer apellido" />
                </v-col>

                <!-- Apellido 2 -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.apellido2" placeholder="Segundo apellido" />
                </v-col>

                <!-- Fecha nacimiento -->
                <v-col cols="12" md="6">

                    <v-menu v-model="menuNacimiento" :close-on-content-click="false" transition="scale-transition"
                        offset-y>
                        <template v-slot:activator="{ props }">
                            <v-text-field v-model="form.fechaNacimiento" placeholder="Fecha nacimiento" readonly
                                v-bind="props" prepend-inner-icon="mdi-calendar" />
                        </template>

                        <v-date-picker v-model="form.fechaNacimiento" @update:model-value="menuNacimiento = false" />

                    </v-menu>

                </v-col>

                <!-- Fecha alta -->
                <v-col cols="12" md="6">

                    <v-menu v-model="menuAlta" :close-on-content-click="false" transition="scale-transition" offset-y>
                        <template v-slot:activator="{ props }">
                            <v-text-field v-model="form.fechaAlta" placeholder="Fecha alta" readonly v-bind="props"
                                prepend-inner-icon="mdi-calendar" />
                        </template>

                        <v-date-picker v-model="form.fechaAlta" @update:model-value="menuAlta = false" />

                    </v-menu>

                </v-col>

                <!-- Teléfono 1 -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.telefono1" placeholder="Teléfono 1" />
                </v-col>

                <!-- Teléfono 2 -->
                <v-col cols="12" md="6">
                    <v-text-field v-model="form.telefono2" placeholder="Teléfono 2" />
                </v-col>

                <!-- Email -->
                <v-col cols="12">
                    <v-text-field v-model="form.email" placeholder="Email" />
                </v-col>

                <!-- Estado civil -->
                <v-col cols="12" md="6">
                    <label class="text-subtitle-2">Estado civil</label>
                    <v-radio-group v-model="form.estadoCivil">
                        <v-radio label="Soltero" value="S"></v-radio>
                        <v-radio label="Casado" value="C"></v-radio>
                    </v-radio-group>
                </v-col>

                <!-- Formación -->
                <v-col cols="12" md="6">
                    <label class="text-subtitle-2">Formación universitaria</label>
                    <v-radio-group v-model="form.formacionUniversitaria">
                        <v-radio label="Sí" value="S"></v-radio>
                        <v-radio label="No" value="N"></v-radio>
                    </v-radio-group>
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

.v-row--density-default {
    --v-col-gap-x: 10px;
    --v-col-gap-y: 0px;
}

.my-fixed {
    position: fixed;
    top: 70px;
    left: 20px;
}
</style>