package com.biblioteca.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/**
 * Feign Client para comunicarse con security-service.
 * Obtiene los nombres de roles asignados a un usuario.
 */
@FeignClient(name = "security-service")
public interface SecurityClient {

    // Coincide con SecurityController#verRolesDeUsuario -> GET /api/security/roles/usuario/{usuarioId}
    @GetMapping("/api/security/roles/usuario/{usuarioId}")
    List<String> obtenerRoles(@PathVariable("usuarioId") Long usuarioId);
}
