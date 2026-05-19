package com.biblioteca.ms_inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LibroCreateDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 200, message = "El titulo no puede superar los 200 caracteres")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede superar los 100 caracteres")
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 20, message = "El ISBN debe tener entre 10 y 20 caracteres")
    private String isbn;

    @Size(max = 100, message = "La editorial no puede superar los 100 caracteres")
    private String editorial;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;
}
