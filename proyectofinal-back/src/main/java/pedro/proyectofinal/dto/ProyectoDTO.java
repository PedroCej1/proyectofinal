package pedro.proyectofinal.dto;

import lombok.Data;

/**
 * DTO simple de Proyecto
 */
@Data
public class ProyectoDTO {

    private Integer idProyecto;
    private String descripcion;
    private String lugar;
}