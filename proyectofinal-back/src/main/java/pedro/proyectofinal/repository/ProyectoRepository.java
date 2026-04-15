package pedro.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pedro.proyectofinal.model.Proyecto;

import java.util.List;

/**
 * Repository para la entidad Proyecto.
 */
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    /**
     * Buscar proyectos activos
     *
     * @return Lista de proyectos activos
     */
    List<Proyecto> findByFechaBajaIsNull();

    /**
     * Buscar proyectos por descripción
     *
     * @param descripcion descripcion del proyecto
     * @return lista de proyectos
     */
    List<Proyecto> findByDescripcionContainingIgnoreCase(String descripcion);

    /**
     * Devuelve true si el proyecto tiene empleados asignados
     */
    @Query("""
        SELECT CASE WHEN COUNT(ep) > 0 THEN true ELSE false END
        FROM Proyecto p
        JOIN p.empleados ep
        WHERE p.idProyecto = :id
    """)
    boolean existsEmpleadosByProyecto(@Param("id") Integer id);
}