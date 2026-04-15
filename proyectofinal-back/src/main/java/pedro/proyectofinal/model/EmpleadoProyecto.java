package pedro.proyectofinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Entidad que representa la relación entre empleados y proyectos.
 */
@Entity
@Table(name = "PR_EMPLEADOS_PROYECTO", schema = "PRACTICA")
@Data
public class EmpleadoProyecto {

    @EmbeddedId
    private EmpleadoProyectoId id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEmpleado")
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProyecto")
    @JoinColumn(name = "ID_PROYECTO")
    private Proyecto proyecto;

    @Column(name = "F_ALTA")
    private LocalDate fechaAlta;
}
