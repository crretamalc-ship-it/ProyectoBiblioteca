package example.ms_favoritos_listas.client;

import example.ms_favoritos_listas.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Feign hacia user-service para validar el propietario de la lista. */
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/email/{email}")
    UsuarioDto obtenerPorEmail(@PathVariable("email") String email);
}
