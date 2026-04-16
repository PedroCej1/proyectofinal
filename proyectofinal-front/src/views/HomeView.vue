<script setup lang="ts">

import { ref } from 'vue'

const API = "http://localhost:8000"

const graficasEmpleados = [
    { title: "Altas por año", url: "/grafica/altas-anyo" },
    { title: "Bajas por año", url: "/grafica/bajas-anyo" },
    { title: "Distribución por año", url: "/grafica/dist-anyo" },
    { title: "Distribución edad", url: "/grafica/dist-edad" },
    { title: "Estado civil", url: "/grafica/ecivil" },
    { title: "Empleados por año", url: "/grafica/emp-anyo" },
    { title: "Top 5 antiguos", url: "/grafica/top5emp-ant" },
    { title: "Top 5 recientes", url: "/grafica/top5emp-rec" },
]

const graficasProyectos = [
    { title: "Duración proyectos", url: "/grafica/proy-durac" },
    { title: "Proyectos por empleado", url: "/grafica/proy-emp" },
    { title: "Proyectos por lugar", url: "/grafica/proy-lugar" },
    { title: "Proyectos total", url: "/grafica/proy" },
    { title: "Top proyectos con más empleados", url: "/grafica/proymasemp" },
]

const buildUrl = (path: string) => `${API}${path}`


const refreshAnalytics = async () => {
    await fetch(`${API}/run-analytics`)
}
</script>

<template>
    <v-container fluid class="pa-6">

        <!-- BIENVENIDA -->
        <div class="welcome-container">
            <img src="/logo_blue.png" alt="Logo" class="logo-big" />
            <h1 class="welcome-title">Bienvenido al gestor de Empleados y Proyectos</h1>
            <p>Aquí podrás dar de alta a nuevos Empleados, Proyectos, Gestionar como se relacionan entre sí y mucho
                más...</p>
        </div>

        <!-- TITULO -->
        <div class="text-center mb-8">
            <h1>📊 Dashboard de Analíticas📊</h1>
        </div>
        <v-row justify="center" class="mb-6">
            <v-btn color="primary" size="large" @click="refreshAnalytics">
                Actualizar datos
            </v-btn>
        </v-row>

        <!-- EMPLEADOS -->
        <h2 class="mb-4 text-center">👷 Empleados</h2>

        <v-row justify="center">
            <v-col v-for="g in graficasEmpleados" :key="g.url" cols="12" md="6" lg="4" class="d-flex justify-center">
                <v-card class="pa-4 text-center" elevation="4" style="width:100%; max-width:450px">

                    <h3 class="mb-2">{{ g.title }}</h3>

                    <v-img :src="buildUrl(g.url)" height="260" contain class="rounded-lg" />

                </v-card>
            </v-col>
        </v-row>

        <v-divider class="my-10" />

        <!-- PROYECTOS -->
        <h2 class="mb-4 text-center">🏗️ Proyectos</h2>

        <v-row justify="center">
            <v-col v-for="g in graficasProyectos" :key="g.url" cols="12" md="6" lg="4" class="d-flex justify-center">
                <v-card class="pa-4 text-center" elevation="4" style="width:100%; max-width:450px">

                    <h3 class="mb-2">{{ g.title }}</h3>

                    <v-img :src="buildUrl(g.url)" height="260" contain class="rounded-lg" />

                </v-card>
            </v-col>
        </v-row>

    </v-container>
</template>

<style scoped>
h1 {
    font-weight: 700;
}

h2 {
    font-weight: 600;
}

.welcome-container {
    height: 80vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.logo-big {
    width: 600px;
    height: auto;
    margin-bottom: 20px;
}

.welcome-title {
    font-size: 32px;
    font-weight: 600;
}
</style>
