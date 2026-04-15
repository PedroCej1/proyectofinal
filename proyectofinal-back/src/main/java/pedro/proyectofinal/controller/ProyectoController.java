package pedro.proyectofinal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.proyectofinal.model.Proyecto;
import pedro.proyectofinal.service.ProyectoService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para proyectos
 */
@RestController
@RequestMapping("/proyectos")
@Slf4j
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodos() {

        log.info("GET proyectos");

        return ResponseEntity.ok(proyectoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerPorId(@PathVariable Integer id) {

        log.info("GET proyecto {}", id);

        Optional<Proyecto> proyecto = proyectoService.buscarPorId(id);

        return proyecto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody Proyecto proyecto) {

        log.info("POST crear proyecto");

        return ResponseEntity.ok(proyectoService.guardar(proyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(
            @PathVariable Integer id,
            @RequestBody Proyecto proyecto) {

        log.info("PUT actualizar proyecto {}", id);

        proyecto.setIdProyecto(id);

        return ResponseEntity.ok(proyectoService.guardar(proyecto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.info("DELETE proyecto {}", id);

        proyectoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/baja")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(proyectoService.darDeBaja(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}/reactivar")
    public ResponseEntity<String> reactivar(@PathVariable Integer id) {

        proyectoService.reactivarProyecto(id);

        return ResponseEntity.ok("Proyecto reactivado correctamente");
    }



}