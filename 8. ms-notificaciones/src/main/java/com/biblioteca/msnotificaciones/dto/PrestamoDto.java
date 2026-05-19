package com.biblioteca.msnotificaciones.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamoDto {
    private Long id;
    private String emailUsuario;
    private Long libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estado;
}
