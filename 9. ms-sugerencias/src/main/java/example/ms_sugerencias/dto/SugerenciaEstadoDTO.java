package example.ms_sugerencias.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Datos para cambiar el estado de una sugerencia")
public class SugerenciaEstadoDTO {
    @NotBlank
    @Pattern(regexp = "PENDIENTE|APROBADA|RECHAZADA|COMPRADA",
             message = "Estado debe ser PENDIENTE, APROBADA, RECHAZADA o COMPRADA")
    @Schema(description = "Nuevo estado de la sugerencia", example = "APROBADA")
    private String estado;
}
