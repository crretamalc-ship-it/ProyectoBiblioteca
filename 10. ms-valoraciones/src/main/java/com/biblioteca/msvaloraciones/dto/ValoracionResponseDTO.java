package com.biblioteca.msvaloraciones.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ValoracionResponseDTO {
    private Long id;
    private Long libroId;
    private String emailUsuario;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaCreacion;
}
