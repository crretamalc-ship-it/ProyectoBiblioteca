package com.biblioteca.ms_inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos para crear un libro")
public class LibroCreateDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 200, message = "El titulo no puede superar los 200 caracteres")
    @Schema(description = "Titulo del libro", example = "Cien anios de soledad")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede superar los 100 caracteres")
    @Schema(description = "Autor del libro", example = "Gabriel Garcia Marquez")
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 20, message = "El ISBN debe tener entre 10 y 20 caracteres")
    @Schema(description = "Codigo ISBN del libro", example = "9780307474728")
    private String isbn;

    @Size(max = 100, message = "La editorial no puede superar los 100 caracteres")
    @Schema(description = "Editorial del libro", example = "Sudamericana")
    private String editorial;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad de ejemplares disponibles", example = "5")
    private Integer stock;
}
