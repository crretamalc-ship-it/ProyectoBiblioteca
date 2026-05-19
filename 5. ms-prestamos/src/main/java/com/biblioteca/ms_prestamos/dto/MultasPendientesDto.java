package com.biblioteca.ms_prestamos.dto;

import lombok.Data;

/**
 * Respuesta del endpoint /multas/pendientes/{email} de ms-multas.
 */
@Data
public class MultasPendientesDto {
    private String email;
    private Boolean tienePendientes;
    private Long cantidad;
}
