package com.biblioteca.ms_prestamos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos para registrar un prestamo")
public class PrestamoCreateDTO {

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "El emailUsuario debe ser un correo valido")
    @Schema(description = "Correo del usuario que solicita el prestamo", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    @NotNull(message = "El libroId es obligatorio")
    @Positive(message = "El libroId debe ser positivo")
    @Schema(description = "ID del libro solicitado", example = "10")
    private Long libroId;

    // fechaDevolucion puede venir o no; si viene debe ser futura
    @Future(message = "La fechaDevolucion debe ser una fecha futura")
    @Schema(description = "Fecha esperada de devolucion", example = "2026-07-10")
    private LocalDate fechaDevolucion;
}
