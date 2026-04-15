package pedro.proyectofinal.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.proyectofinal.model.Empleado;
import pedro.proyectofinal.service.EmpleadoService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para empleados
 */
@RestController
@RequestMapping("/empleados")
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    /**
     * Constructor
     *
     * @param empleadoService servicio empleado
     */
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    /**
     * Obtener todos los empleados
     *
     * @return lista empleados
     */
    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodos() {

        log.info("Petición GET - obtener todos los empleados");

        return ResponseEntity.ok(empleadoService.obtenerTodos());
    }

    /**
     * Obtener empleado por id
     *
     * @param id id empleado
     * @return empleado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Integer id) {

        log.info("Petición GET - obtener empleado {}", id);

        Optional<Empleado> empleado = empleadoService.buscarPorId(id);

        return empleado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear empleado
     *
     * @param empleado empleado
     * @return empleado creado
     */
    @PostMapping
    public ResponseEntity<Empleado> crear(@Valid @RequestBody Empleado empleado) {

        log.info("Petición POST - crear empleado");
        empleadoService.validarEmpleado(empleado);

        return ResponseEntity.ok(empleadoService.guardar(empleado));
    }

    /**
     * Actualizar empleado
     *
     * @param id id empleado
     * @param empleado datos
     * @return empleado actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(
            @PathVariable Integer id,
            @RequestBody Empleado empleado) {

        log.info("Petición PUT - actualizar empleado {}", id);

        empleado.setIdEmpleado(id);

        return ResponseEntity.ok(empleadoService.guardar(empleado));
    }

    /**
     * Eliminar empleado
     *
     * @param id id empleado
     * @return response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.info("Petición DELETE - eliminar empleado {}", id);

        empleadoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Dar de Baja a un empleado sin eliminarlo
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}/baja")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(empleadoService.darDeBajaEmpleado(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * REactoivar empleado
     * @param id
     * @return
     */

    @PutMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable Integer id) {

        empleadoService.reactivarEmpleado(id);

        return ResponseEntity.ok().build();
    }

}