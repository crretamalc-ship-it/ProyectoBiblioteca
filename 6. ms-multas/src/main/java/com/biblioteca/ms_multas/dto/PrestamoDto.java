package com.biblioteca.ms_multas.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO ligero que refleja la estructura de un Prestamo expuesto por ms-prestamos.
 */
@Data
public class PrestamoDto {
    private Long id;
    private String emailUsuario;
    private Long libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estado;
}
