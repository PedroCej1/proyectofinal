package pedro.proyectofinal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable; /**
 * Clase auxiliar para la clave compuesta de EmpleadoProyecto.
 */
@Data
@NoArgsConstructor
public class EmpleadoProyectoId implements Serializable {
    private Integer idEmpleado;
    private Integer idProyecto;
}
