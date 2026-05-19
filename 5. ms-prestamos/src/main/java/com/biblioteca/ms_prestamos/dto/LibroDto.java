package com.biblioteca.ms_prestamos.dto;

import lombok.Data;

/**
 * DTO ligero del libro expuesto por ms-inventario.
 * Permite a ms-prestamos validar la existencia del libro y su stock antes
 * de registrar un prestamo.
 */
@Data
public class LibroDto {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editorial;
    private Integer stock;
}
