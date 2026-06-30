package example.ms_favoritos_listas;

import example.ms_favoritos_listas.model.ListaFavoritos;
import example.ms_favoritos_listas.repository.ListaFavoritosRepository;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final ListaFavoritosRepository repository;

    public DataLoader(ListaFavoritosRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 1; i <= 8; i++) {
            ListaFavoritos lista = ListaFavoritos.builder()
                    .nombre("Lista " + i + " - " + faker.book().genre())
                    .descripcion(faker.lorem().sentence())
                    .emailUsuario("socio" + ((i % 5) + 1) + "@biblioteca.cl")
                    .publica(random.nextBoolean())
                    .build();
            repository.save(lista);
        }
    }
}
