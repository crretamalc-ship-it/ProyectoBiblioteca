package com.biblioteca.ms_prestamos.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrestamoUpdateDTO {

    private LocalDate fechaDevolucion;

    @Pattern(regexp = "ACTIVO|DEVUELTO|VENCIDO",
             message = "El estado debe ser ACTIVO, DEVUELTO o VENCIDO")
    private String estado;
}
