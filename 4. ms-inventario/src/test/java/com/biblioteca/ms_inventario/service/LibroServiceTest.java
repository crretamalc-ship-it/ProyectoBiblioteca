package com.biblioteca.ms_inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.biblioteca.ms_inventario.model.Libro;
import com.biblioteca.ms_inventario.repository.LibroRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {

    @Mock
    private LibroRepository repository;

    @InjectMocks
    private LibroService service;

    @Test
    void testFindAll() {
        Libro libroUno = new Libro();
        libroUno.setId(1L);
        libroUno.setTitulo("Cien anios de soledad");
        libroUno.setAutor("Gabriel Garcia Marquez");
        libroUno.setIsbn("9780000000001");
        libroUno.setEditorial("Sudamericana");
        libroUno.setStock(5);

        Libro libroDos = new Libro();
        libroDos.setId(2L);
        libroDos.setTitulo("El principito");
        libroDos.setAutor("Antoine de Saint-Exupery");
        libroDos.setIsbn("9780000000002");
        libroDos.setEditorial("Reynal & Hitchcock");
        libroDos.setStock(3);

        when(repository.findAll()).thenReturn(List.of(libroUno, libroDos));

        List<Libro> resultado = service.listarTodo();

        assertEquals(2, resultado.size());
        assertEquals("Cien anios de soledad", resultado.get(0).getTitulo());
        assertEquals("El principito", resultado.get(1).getTitulo());
        verify(repository).findAll();
    }
}
