import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '@/views/HomeView.vue'
import ProyectosView from '@/views/ProyectosView.vue'
import EmpleadosView from '@/views/EmpleadosView.vue'
import SettingsView from '@/views/SettingsView.vue'
import EmpleadoNuevo from '@/views/EmpleadoFormView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/proyectos',
      name: 'proyectos',
      component: ProyectosView,
    },
    {
      path: '/empleados',
      name: 'empleados',
      component: EmpleadosView,
    },
    {
      path: '/settings',
      name: 'settings',
      component: SettingsView,
    },
    {
      path: '/empleados/nuevo',
      name: 'empleado-nuevo',
      component: EmpleadoNuevo,
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('@/views/NotFoundView.vue'),
    },
    {
      path: '/proyectos/:id',
      name: 'proyecto-detalle',
      component: () => import('@/views/ProyectoDetalle.vue'),
    },
    {
      path: '/proyectos/nuevo',
      name: 'proyecto-nuevo',
      component: () => import('@/views/ProyectoFormView.vue'),
    },
    {
      path: '/asignaciones',
      name: 'asignaciones',
      component: () => import('@/views/AsignacionView.vue'),
    },
    {
      path: '/empleados/:id',
      name: 'empleado-detalle',
      component: () => import('@/views/EmpleadoDetalle.vue'),
    },
  ],
})

export default router
