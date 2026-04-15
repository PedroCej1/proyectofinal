package pedro.proyectofinal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pedro.proyectofinal.dto.EmpleadoDTO;
import pedro.proyectofinal.model.*;
import pedro.proyectofinal.repository.*;
import pedro.proyectofinal.dto.ProyectoDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de relación Empleado-Proyecto
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoProyectoService {

    private final EmpleadoRepository empleadoRepository;
    private final ProyectoRepository proyectoRepository;
    private final EmpleadoProyectoRepository empleadoProyectoRepository;

    /**
     * ASIGNAR empleado a proyecto
     */
    @Transactional
    public void asignar(Integer idEmpleado, Integer idProyecto) {

        log.info("Asignando empleado {} a proyecto {}", idEmpleado, idProyecto);

        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Proyecto proyecto = proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        EmpleadoProyectoId id = new EmpleadoProyectoId();
        id.setIdEmpleado(idEmpleado);
        id.setIdProyecto(idProyecto);

        EmpleadoProyecto ep = new EmpleadoProyecto();
        ep.setId(id);
        ep.setEmpleado(empleado);
        ep.setProyecto(proyecto);
        ep.setFechaAlta(LocalDate.now());

        empleadoProyectoRepository.save(ep);

        log.info("Asignación completada");
    }

    /**
     * DESASIGNAR empleado de proyecto
     */
    @Transactional
    public void desasignar(Integer idEmpleado, Integer idProyecto) {

        log.info("Desasignando empleado {} de proyecto {}", idEmpleado, idProyecto);

        EmpleadoProyectoId id = new EmpleadoProyectoId();
        id.setIdEmpleado(idEmpleado);
        id.setIdProyecto(idProyecto);

        if (!empleadoProyectoRepository.existsById(id)) {
            throw new RuntimeException("La relación no existe");
        }

        empleadoProyectoRepository.deleteById(id);

        log.info("Desasignación completada");
    }

    /**
     * OBTENER empleados de un proyecto (DTO seguro)
     */
    public List<EmpleadoDTO> getEmpleadosByProyecto(Integer idProyecto) {

        return empleadoProyectoRepository.findByProyecto_IdProyecto(idProyecto)
                .stream()
                .map(ep -> {
                    EmpleadoDTO dto = new EmpleadoDTO();
                    dto.setIdEmpleado(ep.getEmpleado().getIdEmpleado());
                    dto.setNombre(ep.getEmpleado().getNombre());
                    dto.setApellido1(ep.getEmpleado().getApellido1());
                    dto.setEmail(ep.getEmpleado().getEmail());
                    return dto;
                })
                .toList();
    }


    public List<ProyectoDTO> getProyectosByEmpleado(Integer idEmpleado) {

        return empleadoProyectoRepository.findByEmpleado_IdEmpleado(idEmpleado)
                .stream()
                .map(ep -> {
                    ProyectoDTO dto = new ProyectoDTO();
                    dto.setIdProyecto(ep.getProyecto().getIdProyecto());
                    dto.setDescripcion(ep.getProyecto().getDescripcion());
                    dto.setLugar(ep.getProyecto().getLugar());
                    return dto;
                })
                .toList();
    }
}