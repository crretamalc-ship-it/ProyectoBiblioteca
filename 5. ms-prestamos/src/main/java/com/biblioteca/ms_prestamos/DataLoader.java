package com.biblioteca.ms_prestamos;

import com.biblioteca.ms_prestamos.model.Prestamo;
import com.biblioteca.ms_prestamos.repository.PrestamoRepository;
import java.time.LocalDate;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final PrestamoRepository repository;

    public DataLoader(PrestamoRepository repository) {
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
            LocalDate fechaPrestamo = LocalDate.now().minusDays(random.nextInt(20) + 1);
            Prestamo prestamo = new Prestamo();
            prestamo.setEmailUsuario("socio" + faker.number().numberBetween(1, 6) + "@biblioteca.cl");
            prestamo.setLibroId((long) ((i % 12) + 1));
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaPrestamo.plusDays(random.nextInt(15) + 7));
            prestamo.setEstado(random.nextInt(4) == 0 ? "DEVUELTO" : "ACTIVO");
            repository.save(prestamo);
        }
    }
}
