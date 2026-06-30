package example.ms_sugerencias;

import example.ms_sugerencias.model.Sugerencia;
import example.ms_sugerencias.repository.SugerenciaRepository;
import java.time.LocalDateTime;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final SugerenciaRepository repository;

    public DataLoader(SugerenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();
        String[] estados = {"PENDIENTE", "APROBADA", "RECHAZADA", "COMPRADA"};
        for (int i = 1; i <= 10; i++) {
            Sugerencia sugerencia = Sugerencia.builder()
                    .titulo(faker.book().title())
                    .autor(faker.book().author())
                    .isbn("97900000000" + String.format("%02d", i))
                    .comentario(faker.lorem().sentence())
                    .estado(estados[random.nextInt(estados.length)])
                    .fechaSugerencia(LocalDateTime.now().minusDays(random.nextInt(46)))
                    .emailSocio("socio" + ((i % 5) + 1) + "@biblioteca.cl")
                    .build();
            repository.save(sugerencia);
        }
    }
}
