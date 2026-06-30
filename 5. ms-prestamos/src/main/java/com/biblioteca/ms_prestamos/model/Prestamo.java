package com.biblioteca.ms_prestamos.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Entidad Prestamo")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del prestamo", example = "15")
    private Long id;

    // Correo del usuario (validado contra user-service)
    @Schema(description = "Correo del usuario que solicita el prestamo", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    // ID del libro (validado contra ms-inventario
    @Schema(description = "ID del libro prestado", example = "10")
    private Long libroId;

    @Schema(description = "Fecha en que se registra el prestamo", example = "2026-06-29")
    private LocalDate fechaPrestamo;
    @Schema(description = "Fecha esperada de devolucion", example = "2026-07-10")
    private LocalDate fechaDevolucion;
    @Schema(description = "Estado del prestamo", example = "ACTIVO")
    private String estado; // "ACTIVO", "DEVUELTO"
}
