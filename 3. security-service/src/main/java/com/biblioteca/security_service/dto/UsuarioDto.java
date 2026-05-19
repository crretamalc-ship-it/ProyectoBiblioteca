package com.biblioteca.security_service.dto;

import lombok.Data;

/**
 * DTO ligero que representa solo los datos del usuario que security-service necesita
 * cuando consulta a user-service a traves de Feign.
 */
@Data
public class UsuarioDto {
    private Long id;
    private String email;
    private String nombre;
}
