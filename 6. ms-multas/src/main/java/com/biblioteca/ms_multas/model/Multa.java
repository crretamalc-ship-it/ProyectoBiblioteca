package com.biblioteca.ms_multas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Correo del usuario que tiene la multa (se valida contra user-service)
    private String emailUsuario;

    // Prestamo que origina la multa (se valida contra ms-prestamos)
    private Long prestamoId;

    // Dias de retraso al momento del calculo
    private Integer diasRetraso;

    // Monto total de la multa
    private BigDecimal monto;

    private LocalDate fechaGeneracion;

    private LocalDate fechaPago;

    // PENDIENTE o PAGADA
    private String estado;
}
