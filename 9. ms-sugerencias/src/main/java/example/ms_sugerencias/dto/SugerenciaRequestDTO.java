package example.ms_sugerencias.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SugerenciaRequestDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 2, max = 200)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 2, max = 120)
    private String autor;

    @Size(min = 10, max = 20, message = "El ISBN debe tener entre 10 y 20 caracteres")
    private String isbn;

    @Size(max = 500)
    private String comentario;

    @NotBlank(message = "El emailSocio es obligatorio")
    @Email(message = "El emailSocio debe ser un correo valido")
    private String emailSocio;
}
