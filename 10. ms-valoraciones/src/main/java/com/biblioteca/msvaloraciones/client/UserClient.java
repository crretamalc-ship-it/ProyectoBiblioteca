package com.biblioteca.msvaloraciones.client;

import com.biblioteca.msvaloraciones.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/email/{email}")
    UsuarioDto obtenerPorEmail(@PathVariable("email") String email);
}
