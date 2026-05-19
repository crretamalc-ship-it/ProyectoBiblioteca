package com.biblioteca.msnotificaciones.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificacionResponseDTO {
    private Long id;
    private String emailUsuario;
    private String mensaje;
    private String tipo;
    private LocalDateTime fechaEnvio;
    private String estado;
    private Long recursoId;
}
