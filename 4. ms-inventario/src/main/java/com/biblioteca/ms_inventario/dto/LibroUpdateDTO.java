package com.biblioteca.ms_inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos para actualizar un libro")
public class LibroUpdateDTO {

    @Size(max = 200, message = "El titulo no puede superar los 200 caracteres")
    @Schema(description = "Titulo del libro", example = "Cien anios de soledad")
    private String titulo;

    @Size(max = 100, message = "El autor no puede superar los 100 caracteres")
    @Schema(description = "Autor del libro", example = "Gabriel Garcia Marquez")
    private String autor;

    @Size(min = 10, max = 20, message = "El ISBN debe tener entre 10 y 20 caracteres")
    @Schema(description = "Codigo ISBN del libro", example = "9780307474728")
    private String isbn;

    @Size(max = 100, message = "La editorial no puede superar los 100 caracteres")
    @Schema(description = "Editorial del libro", example = "Sudamericana")
    private String editorial;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad de ejemplares disponibles", example = "5")
    private Integer stock;
}
