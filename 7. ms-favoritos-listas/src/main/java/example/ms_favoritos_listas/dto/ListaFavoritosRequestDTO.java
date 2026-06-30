package example.ms_favoritos_listas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Schema(description = "Datos para crear o actualizar una lista de favoritos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ListaFavoritosRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100)
    @Schema(description = "Nombre de la lista", example = "Lecturas de verano")
    private String nombre;

    @Size(max = 255)
    @Schema(description = "Descripcion de la lista", example = "Libros recomendados para vacaciones")
    private String descripcion;

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "El emailUsuario debe ser un correo valido")
    @Schema(description = "Correo del propietario de la lista", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    @NotNull(message = "Debe indicar si la lista es publica")
    @Schema(description = "Indica si la lista es publica", example = "true")
    private Boolean publica;
}
