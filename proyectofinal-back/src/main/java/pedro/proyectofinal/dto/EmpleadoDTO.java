package pedro.proyectofinal.dto;

import lombok.Data;

/**
 * DTO simple para evitar bucles JSON
 */
@Data
public class EmpleadoDTO {

    private Integer idEmpleado;
    private String nombre;
    private String apellido1;
    private String email;
}