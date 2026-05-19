package example.ms_sugerencias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SugerenciaEstadoDTO {
    @NotBlank
    @Pattern(regexp = "PENDIENTE|APROBADA|RECHAZADA|COMPRADA",
             message = "Estado debe ser PENDIENTE, APROBADA, RECHAZADA o COMPRADA")
    private String estado;
}
