package com.biblioteca.msvaloraciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Datos para crear o actualizar una valoracion")
public class ValoracionRequestDTO {

    @NotNull(message = "El libroId es obligatorio")
    @Positive(message = "El libroId debe ser positivo")
    @Schema(description = "ID del libro valorado", example = "10")
    private Long libroId;

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "El emailUsuario debe ser un correo valido")
    @Schema(description = "Correo del usuario que valora", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    @NotNull(message = "La puntuacion es obligatoria")
    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    @Schema(description = "Puntuacion de 1 a 5", example = "5")
    private Integer puntuacion;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(min = 5, max = 500)
    @Schema(description = "Comentario de la valoracion", example = "Excelente libro, muy recomendado")
    private String comentario;
}
