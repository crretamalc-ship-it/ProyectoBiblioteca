package com.biblioteca.ms_inventario;

import com.biblioteca.ms_inventario.model.Libro;
import com.biblioteca.ms_inventario.repository.LibroRepository;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final LibroRepository repository;

    public DataLoader(LibroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 1; i <= 12; i++) {
            Libro libro = new Libro();
            libro.setTitulo(faker.book().title());
            libro.setAutor(faker.book().author());
            libro.setIsbn("97800000000" + String.format("%02d", i));
            libro.setEditorial(faker.book().publisher());
            libro.setStock(random.nextInt(12) + 1);
            repository.save(libro);
        }
    }
}
