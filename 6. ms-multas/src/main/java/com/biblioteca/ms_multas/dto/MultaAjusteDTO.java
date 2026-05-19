package com.biblioteca.ms_multas.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para ajustar manualmente una multa.
 * Al menos uno de los campos debe venir (validacion en el service).
 */
@Data
public class MultaAjusteDTO {

    @Positive(message = "diasRetraso debe ser positivo")
    private Integer diasRetraso;

    @PositiveOrZero(message = "monto no puede ser negativo")
    private BigDecimal monto;
}
