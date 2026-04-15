package pedro.proyectofinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.proyectofinal.dto.EmpleadoDTO;
import pedro.proyectofinal.dto.ProyectoDTO;
import pedro.proyectofinal.service.EmpleadoProyectoService;

import java.util.List;

/**
 * Controller de relación Empleado-Proyecto
 */
@RestController
@RequestMapping("/empleado-proyecto")
@RequiredArgsConstructor
public class EmpleadoProyectoController {

    private final EmpleadoProyectoService empleadoProyectoService;

    /**
     * ASIGNAR empleado a proyecto
     */
    @PostMapping("/{idEmpleado}/{idProyecto}")
    public ResponseEntity<String> asignar(
            @PathVariable Integer idEmpleado,
            @PathVariable Integer idProyecto) {

        empleadoProyectoService.asignar(idEmpleado, idProyecto);

        return ResponseEntity.ok("Empleado asignado correctamente");
    }

    /**
     * DESASIGNAR empleado de proyecto
     */
    @DeleteMapping("/{idEmpleado}/{idProyecto}")
    public ResponseEntity<String> desasignar(
            @PathVariable Integer idEmpleado,
            @PathVariable Integer idProyecto) {

        empleadoProyectoService.desasignar(idEmpleado, idProyecto);

        return ResponseEntity.ok("Empleado desasignado correctamente");
    }

    /**
     * LISTAR empleados de un proyecto
     */
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<EmpleadoDTO>> getEmpleados(
            @PathVariable Integer idProyecto) {

        return ResponseEntity.ok(
                empleadoProyectoService.getEmpleadosByProyecto(idProyecto)
        );
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<ProyectoDTO>> getProyectosEmpleado(
            @PathVariable Integer idEmpleado) {

        return ResponseEntity.ok(
                empleadoProyectoService.getProyectosByEmpleado(idEmpleado)
        );
    }
}