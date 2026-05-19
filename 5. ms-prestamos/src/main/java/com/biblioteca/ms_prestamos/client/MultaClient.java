package com.biblioteca.ms_prestamos.client;

import com.biblioteca.ms_prestamos.dto.MultasPendientesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client hacia ms-multas. Se utiliza para verificar si un usuario tiene
 * multas pendientes antes de registrar un nuevo prestamo.
 */
@FeignClient(name = "ms-multas")
public interface MultaClient {

    @GetMapping("/multas/pendientes/{email}")
    MultasPendientesDto consultarPendientes(@PathVariable("email") String email);
}
