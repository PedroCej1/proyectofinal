package pedro.proyectofinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Entidad Empleado que representa la tabla EM_EMPLEADOS en la BD PRACTICA.
 * Se utiliza JPA para persistencia y Jackson para control del JSON.
 */
@JsonPropertyOrder({
        "idEmpleado",
        "nif",
        "nombre",
        "apellido1",
        "apellido2",
        "fechaNacimiento",
        "telefono1",
        "telefono2",
        "email",
        "fechaAlta",
        "fechaBaja",
        "estadoCivil",
        "formacionUniversitaria",
        "proyectos"
})

@Entity
@Table(name = "EM_EMPLEADOS", schema = "PRACTICA")
@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLEADO")
    private Integer idEmpleado;

    @Pattern(
            regexp = "^[0-9]{8}[A-Z]$",
            message = "El NIF no tiene un formato válido"
    )
    @NotBlank(message = "El NIF es obligatorio")
    @Column(name = "TX_NIF", unique = true)
    private String nif;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "TX_NOMBRE")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "TX_APELLIDO1")
    private String apellido1;

    @Column(name = "TX_APELLIDO2")
    private String apellido2;


    @Column(name = "F_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Pattern(
            regexp = "^[0-9]{9}$",
            message = "El teléfono debe tener 9 dígitos"
    )
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Column(name = "N_TELEFONO1", unique = true)
    private String telefono1;

    @Column(name = "N_TELEFONO2")
    private String telefono2;

    @Email(message = "Email no valido")
    @NotBlank
    @Column(name = "TX_EMAIL", unique = true)
    private String email;

    @Column(name = "F_ALTA")
    private LocalDate fechaAlta;

    @Column(name = "F_BAJA")
    private LocalDate fechaBaja;

    @Column(name = "CX_EDOCIVIL")
    private String estadoCivil;

    @Column(name = "B_FORMACIONU")
    private String formacionUniversitaria;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private Set<EmpleadoProyecto> proyectos;
}