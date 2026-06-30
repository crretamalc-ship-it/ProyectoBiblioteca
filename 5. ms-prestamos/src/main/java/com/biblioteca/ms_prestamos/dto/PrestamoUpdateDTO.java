package com.biblioteca.ms_prestamos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos para actualizar un prestamo")
public class PrestamoUpdateDTO {

    @Schema(description = "Fecha esperada o real de devolucion", example = "2026-07-10")
    private LocalDate fechaDevolucion;

    @Pattern(regexp = "ACTIVO|DEVUELTO|VENCIDO",
             message = "El estado debe ser ACTIVO, DEVUELTO o VENCIDO")
    @Schema(description = "Estado del prestamo", example = "DEVUELTO")
    private String estado;
}
