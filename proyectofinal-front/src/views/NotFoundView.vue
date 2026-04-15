<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
    router.push('/')
}
</script>

<template>
    <v-container class="d-flex flex-column justify-center align-center text-center" style="height: 80vh;">

        <v-empty-state headline="Whoops, 404" title="Página no encontrada"
            text="La página que buscas no existe o ha sido movida" image="/logo_blue.png" />

        <div class="mt-6">
            <p class="mb-3">
                Prueba a volver al inicio o revisa la URL.
            </p>

            <v-btn color="primary" @click="goHome">
                <v-icon start>mdi-home</v-icon>
                Volver a inicio
            </v-btn>
        </div>

    </v-container>
</template>