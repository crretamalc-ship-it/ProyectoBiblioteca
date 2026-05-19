package example.ms_sugerencias.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class SugerenciaResponseDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private String comentario;
    private String estado;
    private LocalDateTime fechaSugerencia;
    private String emailSocio;
}
