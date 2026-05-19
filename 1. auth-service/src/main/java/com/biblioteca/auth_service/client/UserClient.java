package com.biblioteca.auth_service.client;

import com.biblioteca.auth_service.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserClient {

    // Coincide con UsuarioController#buscarPorEmail -> GET /api/users/email/{email}
    @GetMapping("/api/users/email/{email}")
    UsuarioDto obtenerPorEmail(@PathVariable("email") String email);

    // Coincide con UsuarioController#buscarPorId -> GET /api/users/id/{id}
    @GetMapping("/api/users/id/{id}")
    UsuarioDto obtenerPorId(@PathVariable("id") Long id);
}
