package pedro.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pedro.proyectofinal.model.Empleado;

import java.util.List;
import java.util.Optional;

/**
 * Repository para la entidad Empleado.
 * Proporciona operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    boolean existsByNif(String nif);

    boolean existsByTelefono1(String telefono1);

    boolean existsByEmail(String email);


    /**
     * Busca un empleado por NIF
     *
     * @param nif NIF del empleado
     * @return Optional con el empleado si existe
     */
    Optional<Empleado> findByNif(String nif);

    /**
     * Busca empleados por nombre
     *
     * @param nombre Nombre del empleado
     * @return Lista de empleados
     */
    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca empleados activos (sin fecha baja)
     *
     * @return lista empleados activos
     */
    List<Empleado> findByFechaBajaIsNull();

    List<Empleado>findAll();

    /**
     * Comprueba si un empleado tiene proyectos asignados
     */
    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM Empleado e
        JOIN e.proyectos p
        WHERE e.idEmpleado = :id
    """)
    boolean existsProyectosByEmpleado(@Param("id") Integer id);
}