import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

const API_URL = 'http://localhost:8080/proyectos'

export const useProyectosStore = defineStore('proyectos', {
  state: () => ({
    proyectos: [] as any[],
    loading: false,
    error: null as string | null,
  }),

  actions: {
    /* =========================
       GET TODOS
    ========================= */
    async fetchProyectos() {
      this.loading = true
      this.error = null

      try {
        const res = await fetch(API_URL)

        if (!res.ok) {
          throw new Error('Error al cargar proyectos')
        }

        this.proyectos = await res.json()
      } catch (err: any) {
        this.error = err.message

        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: err.message,
        })
      } finally {
        this.loading = false
      }
    },

    /* =========================
       CREAR PROYECTO
    ========================= */
    async createProyecto(proyecto: any) {
      try {
        const payload = {
          ...proyecto,
          fechaInicio: proyecto.fechaInicio || null,
          fechaFin: proyecto.fechaFin || null,
          imagenUrl: proyecto.imagenUrl || null,
        }

        const res = await fetch(API_URL, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        })

        if (!res.ok) {
          const msg = await res.text()
          throw new Error(msg)
        }

        const data = await res.json()

        this.proyectos.push(data)

        Swal.fire({
          icon: 'success',
          title: 'Proyecto creado',
          timer: 1200,
          showConfirmButton: false,
        })

        return true
      } catch (err: any) {
        Swal.fire({
          icon: 'error',
          title: 'Error al crear proyecto',
          text: err.message,
        })

        return false
      }
    },

    /* =========================
       BAJA PROYECTO
    ========================= */
    async darDeBaja(id: number) {
      try {
        const res = await fetch(`${API_URL}/${id}/baja`, {
          method: 'PUT',
        })

        if (!res.ok) {
          throw new Error(await res.text())
        }

        const p = this.proyectos.find((p) => p.idProyecto === id)
        if (p) {
          p.fechaBaja = new Date().toISOString()
        }

        return true
      } catch (err: any) {
        Swal.fire({
          icon: 'error',
          title: 'Error al dar de baja',
          text: err.message,
        })

        return false
      }
    },

    /* =========================
       REACTIVAR
    ========================= */
    async reactivarProyecto(id: number) {
      try {
        const res = await fetch(`${API_URL}/${id}/reactivar`, {
          method: 'PUT',
        })

        if (!res.ok) {
          throw new Error(await res.text())
        }

        const msg = await res.text()

        const p = this.proyectos.find((p) => p.idProyecto === id)
        if (p) {
          p.fechaBaja = null
        }

        return msg
      } catch (err: any) {
        Swal.fire({
          icon: 'error',
          title: 'Error al reactivar proyecto',
          text: err.message,
        })

        return null
      }
    },

    /* =========================
       GET POR ID
    ========================= */
    async getProyectoById(id: number) {
      try {
        const res = await fetch(`${API_URL}/${id}`)

        if (!res.ok) {
          throw new Error('Proyecto no encontrado')
        }

        return await res.json()
      } catch (err: any) {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: err.message,
        })

        return null
      }
    },

    async fetchProyectosActivos() {
      const res = await fetch('http://localhost:8080/proyectos')

      const data = await res.json()

      return data.filter((p: any) => p.fechaBaja === null)
    },
  },
})
