package com.biblioteca.msnotificaciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificacionRequestDTO {

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "Correo invalido")
    private String emailUsuario;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, max = 500)
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "VENCIMIENTO_PROXIMO|MULTA_GENERADA|RESERVA_DISPONIBLE|GENERICO",
             message = "Tipo no valido")
    private String tipo;

    private Long recursoId;
}
