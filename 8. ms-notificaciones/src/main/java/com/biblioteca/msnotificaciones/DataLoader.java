package com.biblioteca.msnotificaciones;

import com.biblioteca.msnotificaciones.entity.Notificacion;
import com.biblioteca.msnotificaciones.repository.NotificacionRepository;
import java.time.LocalDateTime;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final NotificacionRepository repository;

    public DataLoader(NotificacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();
        String[] tipos = {"VENCIMIENTO_PROXIMO", "MULTA_GENERADA", "RESERVA_DISPONIBLE", "GENERICO"};
        for (int i = 1; i <= 10; i++) {
            Notificacion notificacion = Notificacion.builder()
                    .emailUsuario("socio" + ((i % 5) + 1) + "@biblioteca.cl")
                    .mensaje(faker.lorem().sentence())
                    .tipo(tipos[random.nextInt(tipos.length)])
                    .fechaEnvio(LocalDateTime.now().minusDays(random.nextInt(11)))
                    .estado(random.nextBoolean() ? "ENVIADA" : "PENDIENTE")
                    .recursoId((long) i)
                    .build();
            repository.save(notificacion);
        }
    }
}
