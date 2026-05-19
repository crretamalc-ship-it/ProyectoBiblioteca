package com.biblioteca.ms_prestamos.client;

import com.biblioteca.ms_prestamos.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client para consultar usuarios en user-service.
 * Se usa para verificar que el correo del solicitante este registrado
 * antes de crear un prestamo.
 */
@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/email/{email}")
    UsuarioDto obtenerPorEmail(@PathVariable("email") String email);
}
