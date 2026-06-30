package example.ms_sugerencias.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sugerencias")
                        .version("1.0")
                        .description("Microservicio para propuestas y seguimiento de sugerencias de libros")
                        .contact(new Contact()
                                .name("Equipo Biblioteca")
                                .email("soporte@biblioteca.local"))
                        .license(new License()
                                .name("Proyecto academico")
                                .url("https://www.duoc.cl/")));
    }
}
