package pedro.proyectofinal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pedro.proyectofinal.model.Empleado;
import pedro.proyectofinal.model.Proyecto;
import pedro.proyectofinal.repository.EmpleadoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio relacionada con empleados.
 */
@Service
@Slf4j
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    /**
     * Constructor del servicio
     *
     * @param empleadoRepository repository de empleados
     */
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * Validar empleados
     * @param e
     */
    public void validarEmpleado(Empleado e) {

        LocalDate hoy = LocalDate.now();

        // -------------------------
        // FECHA NACIMIENTO
        // -------------------------
        if (e.getFechaNacimiento() != null &&
                e.getFechaNacimiento().isAfter(hoy)) {

            throw new IllegalArgumentException(
                    "La fecha de nacimiento no puede ser futura"
            );
        }

        // -------------------------
        // FECHA ALTA
        // -------------------------
        if (e.getFechaAlta() != null &&
                e.getFechaAlta().isAfter(hoy.plusMonths(1))) {

            throw new IllegalArgumentException(
                    "La fecha de alta no puede ser superior a un mes desde hoy"
            );
        }
    }


    /**
     * Obtener todos los empleados
     *
     * @return lista empleados
     */
    public List<Empleado> obtenerTodos() {

        log.info("Obteniendo todos los empleados");

        return empleadoRepository.findAll();
    }

    /**
     * Buscar empleado por id
     *
     * @param id id empleado
     * @return empleado encontrado
     */
    public Optional<Empleado> buscarPorId(Integer id) {

        log.info("Buscando empleado con id {}", id);

        return empleadoRepository.findById(id);
    }

    /**
     * Guardar empleado
     *
     * @param empleado empleado a guardar
     * @return empleado guardado
     */
    public Empleado guardar(Empleado empleado) {

        if (empleadoRepository.existsByNif(empleado.getNif())) {
            throw new IllegalArgumentException("El NIF ya está registrado");
        }

        if (empleadoRepository.existsByTelefono1(empleado.getTelefono1())) {
            throw new IllegalArgumentException("El teléfono ya está registrado");
        }

        if (empleadoRepository.existsByEmail(empleado.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        log.info("Guardando empleado {}", empleado.getNombre());

        return empleadoRepository.save(empleado);
    }

    /**
     * Eliminar empleado
     *
     * @param id id empleado
     */
    public void eliminar(Integer id) {

        log.info("Eliminando empleado con id {}", id);

        empleadoRepository.deleteById(id);
    }

    /**
     * Buscar empleados activos
     *
     * @return lista empleados activos
     */
    public List<Empleado> obtenerActivos() {

        log.info("Obteniendo empleados activos");

        return empleadoRepository.findByFechaBajaIsNull();
    }

    /**
     * Buscar por nombre
     *
     * @param nombre nombre empleado
     * @return lista empleados
     */
    public List<Empleado> buscarPorNombre(String nombre) {

        log.info("Buscando empleados por nombre {}", nombre);

        return empleadoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Empleado darDeBajaEmpleado(Integer id) {

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        if (empleadoRepository.existsProyectosByEmpleado(id)) {
            throw new RuntimeException(
                    "No se puede dar de baja el empleado " +
                            empleado.getNombre() +
                            " porque tiene proyectos asignados"
            );
        }

        empleado.setFechaBaja(LocalDate.now());

        return empleadoRepository.save(empleado);
    }

    /**
     * Reactivar a un empleado que estaba de baja
     *
     * @param id
     */
    public void reactivarEmpleado(Integer id) {

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setFechaAlta(LocalDate.now());
        empleado.setFechaBaja(null);

        empleadoRepository.save(empleado);
    }
}