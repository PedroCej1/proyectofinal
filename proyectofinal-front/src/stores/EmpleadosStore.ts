import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

export type Empleado = {
  idEmpleado: number
  nif: string
  nombre: string
  apellido1: string
  apellido2: string
  fechaNacimiento: string
  telefono1: string
  telefono2: string
  email: string
  fechaAlta: string
  fechaBaja: string | null
  estadoCivil: string
  formacionUniversitaria: string
  proyectos: any[]
}
const getErrorMessage = async (res: Response) => {
  try {
    const data = await res.json()
    return data.message || data.error || JSON.stringify(data)
  } catch {
    return await res.text()
  }
}

export const useEmpleadosStore = defineStore('empleados', {
  state: () => ({
    empleados: [] as Empleado[],
    loading: false,
  }),

  actions: {
    // =========================
    // GET EMPLEADOS
    // =========================
    async fetchEmpleados() {
      this.loading = true

      try {
        const res = await fetch('http://localhost:8080/empleados')

        if (!res.ok) {
          const msg = await res.text()

          Swal.fire({
            icon: 'error',
            title: 'Error al cargar empleados',
            text: msg,
          })

          return
        }

        this.empleados = await res.json()
      } catch (err) {
        Swal.fire({
          icon: 'error',
          title: 'Error de conexión',
          text: 'No se pudo conectar con el servidor',
        })
      } finally {
        this.loading = false
      }
    },

    // =========================
    // CREATE EMPLEADO
    // =========================
    async createEmpleado(empleado: Omit<Empleado, 'idEmpleado' | 'proyectos'>) {
      try {
        const res = await fetch('http://localhost:8080/empleados', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            ...empleado,
            proyectos: [],
          }),
        })

        if (!res.ok) {
          const msg = await res.text()

          Swal.fire({
            icon: 'error',
            title: 'Error al crear empleado',
            text: msg,
          })

          return false
        }

        await this.fetchEmpleados()

        Swal.fire({
          icon: 'success',
          title: 'Empleado creado correctamente',
          timer: 1500,
          showConfirmButton: false,
        })

        return true
      } catch (err) {
        Swal.fire({
          icon: 'error',
          title: 'Error de red',
          text: 'No se pudo conectar con el servidor',
        })

        return false
      }
    },

    // =========================
    // BAJA EMPLEADO
    // =========================

    async darDeBaja(idEmpleado: number) {
      const res = await fetch(`http://localhost:8080/empleados/${idEmpleado}/baja`, {
        method: 'PUT',
      })

      const text = await res.text()

      console.log('STATUS:', res.status)
      console.log('OK:', res.ok)
      console.log('BODY:', text)

      if (!res.ok) {
        Swal.fire({
          icon: 'error',
          title: 'No se puede dar de baja',
          text: text,
        })
        return
      }

      await this.fetchEmpleados()

      Swal.fire({
        icon: 'success',
        title: 'Comprueba que se ha dado de baja',
        timer: 1500,
        showConfirmButton: false,
      })
    },

    // =========================
    // ALTA ANTIGUO EMPLEADO
    // =========================

    async reactivarEmpleado(idEmpleado: number) {
      try {
        const res = await fetch(`http://localhost:8080/empleados/${idEmpleado}/reactivar`, {
          method: 'PUT',
        })

        if (!res.ok) {
          const msg = await res.text()

          Swal.fire({
            icon: 'error',
            title: 'Error al reactivar empleado',
            text: msg,
          })

          return
        }

        await this.fetchEmpleados()

        Swal.fire({
          icon: 'success',
          title: 'Empleado reactivado',
          timer: 1500,
          showConfirmButton: false,
        })
      } catch (e) {
        Swal.fire({
          icon: 'error',
          title: 'Error de conexión',
        })
      }
    },

    async fetchEmpleadosActivos() {
      const res = await fetch('http://localhost:8080/empleados')

      const data = await res.json()

      return data.filter((e: any) => e.fechaBaja === null)
    },
  },
})
