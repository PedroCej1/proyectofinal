<script setup>
import { useSettingsStore } from '../stores/settings'

const settings = useSettingsStore()
</script>

<template>
  <v-container>
    <v-btn @click="settings.toggleTheme()">
      Cambiar tema (actual: {{ settings.theme }})
    </v-btn>

    <p>Idioma: {{ settings.language }}</p>
    <p>Fuente: {{ settings.fontSize }}px</p>
  </v-container>
</template>