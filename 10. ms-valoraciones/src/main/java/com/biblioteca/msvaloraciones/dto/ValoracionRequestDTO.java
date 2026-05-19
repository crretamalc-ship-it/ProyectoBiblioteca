package com.biblioteca.msvaloraciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ValoracionRequestDTO {

    @NotNull(message = "El libroId es obligatorio")
    @Positive(message = "El libroId debe ser positivo")
    private Long libroId;

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "El emailUsuario debe ser un correo valido")
    private String emailUsuario;

    @NotNull(message = "La puntuacion es obligatoria")
    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    private Integer puntuacion;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(min = 5, max = 500)
    private String comentario;
}
