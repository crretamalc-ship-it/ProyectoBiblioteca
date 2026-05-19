package example.ms_sugerencias.client;

import example.ms_sugerencias.dto.LibroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Feign hacia ms-inventario para chequear si el ISBN sugerido ya existe en el catalogo. */
@FeignClient(name = "ms-inventario")
public interface LibroClient {
    @GetMapping("/inventario/ver/isbn/{isbn}")
    LibroDto buscarPorIsbn(@PathVariable("isbn") String isbn);
}
