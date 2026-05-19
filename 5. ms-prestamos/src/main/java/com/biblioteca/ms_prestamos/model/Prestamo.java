package com.biblioteca.ms_prestamos.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Correo del usuario (validado contra user-service)
    private String emailUsuario;

    // ID del libro (validado contra ms-inventario
    private Long libroId;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estado; // "ACTIVO", "DEVUELTO"
}
