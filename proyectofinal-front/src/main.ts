import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import '@mdi/font/css/materialdesignicons.css'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import './assets/styles/main.css'

const myLightTheme = {
  dark: false,
  colors: {
    background: '#e8f2ff',
    surface: '#ffffff',
    primary: '#1265FF',
    secondary: '#0A275C',
    accent: '#3d83ff',
    info: '#7ab8ff',
    error: '#ff5252',
    success: '#4caf50',
    warning: '#fb8c00',
  },
}

const myDarkTheme = {
  dark: true,
  colors: {
    background: '#1a1a1a',
    surface: '#222222',
    primary: '#1265FF',
    secondary: '#0A275C',
    accent: '#3d83ff',
    info: '#7ab8ff',
  },
}

const vuetify = createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: 'light',
    themes: {
      light: myLightTheme,
      dark: myDarkTheme,
    },
  },
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi,
    },
  },
})

createApp(App).use(createPinia()).use(router).use(vuetify).mount('#app')
