package com.biblioteca.ms_inventario.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LibroUpdateDTO {

    @Size(max = 200, message = "El titulo no puede superar los 200 caracteres")
    private String titulo;

    @Size(max = 100, message = "El autor no puede superar los 100 caracteres")
    private String autor;

    @Size(min = 10, max = 20, message = "El ISBN debe tener entre 10 y 20 caracteres")
    private String isbn;

    @Size(max = 100, message = "La editorial no puede superar los 100 caracteres")
    private String editorial;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;
}
