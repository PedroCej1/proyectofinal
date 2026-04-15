import { defineStore } from 'pinia'

export const useSettingsStore = defineStore('settings', {
  state: () => ({
    theme: 'light',   // light | dark
    language: 'es',
    fontSize: 16
  }),

  actions: {
    toggleTheme() {
      this.theme = this.theme === 'light' ? 'dark' : 'light'
    },

    setLanguage(lang) {
      this.language = lang
    },

    setFontSize(size) {
      this.fontSize = size
    }
  }
})