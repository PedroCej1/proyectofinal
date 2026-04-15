package pedro.proyectofinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

/**
 * Entidad que representa un proyecto.
 */
@Entity
@Table(name = "PR_PROYECTOS", schema = "PRACTICA")
@Data
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROYECTO", nullable = false)
    private Integer idProyecto;

    @Column(name = "TX_DESCRIPCION", nullable = false, length = 125)
    @NotNull(message = "La descripcion es obligatoria")
    private String descripcion;

    @Column(name = "F_INICIO", nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaInicio;

    @Column(name = "F_FIN")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaFin;

    @Column(name = "F_BAJA")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaBaja;

    @NotBlank(message = "El lugar es obligatorio")
    @Column(name = "TX_LUGAR", length = 30)
    private String lugar;

    @Column(name = "TX_OBSERVACIONES", length = 300)
    private String observaciones;

    @JsonIgnore
    @OneToMany(mappedBy = "proyecto")
    private Set<EmpleadoProyecto> empleados;

    @Column(name = "IMAGEN_URL")
    private String imagenUrl;

    public void setFechaBaja(LocalDate now) {
        this.fechaBaja = now;
    }
}