package example.ms_sugerencias.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Datos para crear o actualizar una sugerencia")
public class SugerenciaRequestDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 2, max = 200)
    @Schema(description = "Titulo del libro sugerido", example = "El principito")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 2, max = 120)
    @Schema(description = "Autor del libro sugerido", example = "Antoine de Saint-Exupery")
    private String autor;

    @Size(min = 10, max = 20, message = "El ISBN debe tener entre 10 y 20 caracteres")
    @Schema(description = "ISBN del libro sugerido", example = "9780156012195")
    private String isbn;

    @Size(max = 500)
    @Schema(description = "Comentario del socio", example = "Seria bueno tener este clasico en la biblioteca")
    private String comentario;

    @NotBlank(message = "El emailSocio es obligatorio")
    @Email(message = "El emailSocio debe ser un correo valido")
    @Schema(description = "Correo del socio que sugiere", example = "ana.perez@biblioteca.cl")
    private String emailSocio;
}
