package com.biblioteca.ms_prestamos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrestamoCreateDTO {

    @NotBlank(message = "El emailUsuario es obligatorio")
    @Email(message = "El emailUsuario debe ser un correo valido")
    private String emailUsuario;

    @NotNull(message = "El libroId es obligatorio")
    @Positive(message = "El libroId debe ser positivo")
    private Long libroId;

    // fechaDevolucion puede venir o no; si viene debe ser futura
    @Future(message = "La fechaDevolucion debe ser una fecha futura")
    private LocalDate fechaDevolucion;
}
