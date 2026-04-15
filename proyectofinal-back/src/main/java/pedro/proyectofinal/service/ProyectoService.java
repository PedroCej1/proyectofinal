package pedro.proyectofinal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pedro.proyectofinal.model.Proyecto;
import pedro.proyectofinal.repository.ProyectoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de lógica de negocio para proyectos
 */
@Service
@Slf4j
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    /**
     * Constructor
     *
     * @param proyectoRepository repository proyectos
     */
    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    /**
     * Obtener todos los proyectos
     *
     * @return lista proyectos
     */
    public List<Proyecto> obtenerTodos() {

        log.info("Obteniendo todos los proyectos");

        return proyectoRepository.findAll();
    }

    /**
     * Buscar proyecto por id
     *
     * @param id id proyecto
     * @return proyecto
     */
    public Optional<Proyecto> buscarPorId(Integer id) {

        log.info("Buscando proyecto con id {}", id);

        return proyectoRepository.findById(id);
    }

    /**
     * Guardar proyecto
     *
     * @param proyecto proyecto
     * @return proyecto guardado
     */
    public Proyecto guardar(Proyecto proyecto) {

        log.info("Guardando proyecto {}", proyecto.getDescripcion());

        return proyectoRepository.save(proyecto);
    }

    /**
     * Eliminar proyecto
     *
     * @param id id proyecto
     */
    public void eliminar(Integer id) {

        log.info("Eliminando proyecto {}", id);

        proyectoRepository.deleteById(id);
    }

    /**
     * Obtener proyectos activos
     *
     * @return lista proyectos
     */
    public List<Proyecto> obtenerActivos() {

        log.info("Obteniendo proyectos activos");

        return proyectoRepository.findByFechaBajaIsNull();
    }

    /**
     * Da de baja un proyecto si no tiene empleados asignados
     */
    public Proyecto darDeBaja(Integer id) {

        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // 🔥 VALIDACIÓN
        if (proyectoRepository.existsEmpleadosByProyecto(id)) {
            throw new RuntimeException(
                    "No se puede dar de baja el proyecto " +
                            proyecto.getDescripcion() +
                            " porque tiene asignado al menos un recurso"
            );
        }

        proyecto.setFechaBaja(LocalDate.now());

        return proyectoRepository.save(proyecto);
    }

    public void reactivarProyecto(Integer id) {

        Proyecto p = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        p.setFechaBaja(null);

        // opcional: se puede marcar reactivación
        // p.setFechaInicio(LocalDate.now());

        proyectoRepository.save(p);
    }




}