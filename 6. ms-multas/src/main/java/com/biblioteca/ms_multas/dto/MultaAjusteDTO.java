package com.biblioteca.ms_multas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para ajustar manualmente una multa.
 * Al menos uno de los campos debe venir (validacion en el service).
 */
@Data
@Schema(description = "Datos para ajustar una multa")
public class MultaAjusteDTO {

    @Positive(message = "diasRetraso debe ser positivo")
    @Schema(description = "Nueva cantidad de dias de retraso", example = "4")
    private Integer diasRetraso;

    @PositiveOrZero(message = "monto no puede ser negativo")
    @Schema(description = "Nuevo monto manual de la multa", example = "2000")
    private BigDecimal monto;
}
