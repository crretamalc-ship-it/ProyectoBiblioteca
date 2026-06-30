package com.biblioteca.msnotificaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Datos para crear una notificacion")
public class NotificacionRequestDTO {

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "Correo invalido")
    @Schema(description = "Correo del destinatario", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, max = 500)
    @Schema(description = "Mensaje de la notificacion", example = "Tu prestamo vence pronto")
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "VENCIMIENTO_PROXIMO|MULTA_GENERADA|RESERVA_DISPONIBLE|GENERICO",
             message = "Tipo no valido")
    @Schema(description = "Tipo de notificacion", example = "VENCIMIENTO_PROXIMO")
    private String tipo;

    @Schema(description = "ID opcional del recurso relacionado", example = "15")
    private Long recursoId;
}
