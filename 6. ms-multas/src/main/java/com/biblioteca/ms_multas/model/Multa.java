package com.biblioteca.ms_multas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Entidad Multa")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la multa", example = "4")
    private Long id;

    // Correo del usuario que tiene la multa (se valida contra user-service)
    @Schema(description = "Correo del usuario multado", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    // Prestamo que origina la multa (se valida contra ms-prestamos)
    @Schema(description = "ID del prestamo que origina la multa", example = "15")
    private Long prestamoId;

    // Dias de retraso al momento del calculo
    @Schema(description = "Dias de retraso calculados", example = "3")
    private Integer diasRetraso;

    // Monto total de la multa
    @Schema(description = "Monto total de la multa", example = "1500")
    private BigDecimal monto;

    @Schema(description = "Fecha de generacion de la multa", example = "2026-07-13")
    private LocalDate fechaGeneracion;

    @Schema(description = "Fecha de pago de la multa", example = "2026-07-14")
    private LocalDate fechaPago;

    // PENDIENTE o PAGADA
    @Schema(description = "Estado de la multa", example = "PENDIENTE")
    private String estado;
}
