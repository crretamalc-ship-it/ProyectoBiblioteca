package com.biblioteca.ms_prestamos.dto;

import lombok.Data;

/**
 * DTO ligero del usuario expuesto por user-service.
 * Solo se incluyen los campos que ms-prestamos necesita.
 */
@Data
public class UsuarioDto {
    private Long id;
    private String email;
    private String nombre;
}
