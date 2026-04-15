<script setup lang="ts">
import { useRouter } from 'vue-router'
const router = useRouter()

const go = (path: string) => {
  router.push(path)
}
</script>
<template>
  <v-app>

    <!-- TOOLBAR GLOBAL -->
    <v-toolbar class="d-flex ga-3 toolbar" scroll-behavior="hide" color="#0A275C">

      <v-btn variant="text" class="titulo" @click="go('/')">
        <img src="/logo.png" height="50px" />
      </v-btn>
      <v-spacer />
      <v-btn variant="text" :class="{ active: $route.path === '/proyectos' || $route.path === '/proyectos/nuevo' }"
        @click="go('/proyectos')">
        Proyectos
      </v-btn>
      <v-btn variant="text" :class="{ active: $route.path === '/empleados' || $route.path === '/empleados/nuevo' }"
        @click="go('/empleados')">
        Empleados
      </v-btn>
      <v-btn variant="text" :class="{ active: $route.path === '/asignaciones' }" @click="go('/asignaciones')">
        Asignaciones
      </v-btn>
      <v-spacer />
      <v-spacer />
      <v-btn icon="mdi-cog" class="white" size="small" variant="elevated" @click="go('/settings')" />
    </v-toolbar>

    <v-main>
      <router-view />
    </v-main>

  </v-app>
</template>

<style scoped>
.active {
  opacity: 1;

  border: 1px solid white;
  border-right: 3px solid white;
  border-bottom: 3px solid white;
  font-weight: bold;
  font-size: large;
  margin-right: 10px;
  background: none;

}

.v-btn {
  background: none;
}

.white {
  background: white;

}


.transition-all {
  transition: all 0.3s ease;
}

.toolbar {

  position: sticky;
  top: 0;
  z-index: 1000;
  display: flex;
  flex-wrap: wrap;
  height: auto !important;
  min-height: 64px;

}

@media (max-width: 500px) {

  .toolbar {
    flex-direction: column;
    align-items: stretch;
    height: auto !important;
    min-height: unset !important;
    padding: 8px;
  }

  .toolbar .v-btn {
    width: 100%;
    justify-content: center;
  }

  .toolbar .v-spacer {
    display: none;
  }
}

.titulo {
  font-weight: bold;
  font-size: xx-large;
  padding-bottom: 30px;
  padding-top: 30px;
}
</style>
