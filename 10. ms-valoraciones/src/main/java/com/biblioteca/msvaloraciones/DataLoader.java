package com.biblioteca.msvaloraciones;

import com.biblioteca.msvaloraciones.entity.Valoracion;
import com.biblioteca.msvaloraciones.repository.ValoracionRepository;
import java.time.LocalDateTime;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final ValoracionRepository repository;

    public DataLoader(ValoracionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            Valoracion valoracion = Valoracion.builder()
                    .libroId((long) i)
                    .emailUsuario("socio" + ((i % 5) + 1) + "@biblioteca.cl")
                    .puntuacion(random.nextInt(5) + 1)
                    .comentario(faker.lorem().sentence())
                    .fechaCreacion(LocalDateTime.now().minusDays(random.nextInt(31)))
                    .build();
            repository.save(valoracion);
        }
    }
}
