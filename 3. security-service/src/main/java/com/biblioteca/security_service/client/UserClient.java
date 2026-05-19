package com.biblioteca.security_service.client;

import com.biblioteca.security_service.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/id/{id}")
    UsuarioDto obtenerPorId(@PathVariable("id") Long id);
}
