package pedro.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pedro.proyectofinal.model.EmpleadoProyecto;
import pedro.proyectofinal.model.EmpleadoProyectoId;

import java.util.List;

/**
 * Repository de la relación Empleado-Proyecto
 */
@Repository
public interface EmpleadoProyectoRepository extends JpaRepository<EmpleadoProyecto, EmpleadoProyectoId> {

    /**
     * Devuelve todas las relaciones de un proyecto
     */
    List<EmpleadoProyecto> findByProyecto_IdProyecto(Integer idProyecto);

    /**
     * Devuelve todas las relaciones de un empleado
     */
    List<EmpleadoProyecto> findByEmpleado_IdEmpleado(Integer idEmpleado);
}