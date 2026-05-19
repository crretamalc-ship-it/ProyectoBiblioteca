package example.ms_sugerencias.dto;

import lombok.Data;

@Data
public class LibroDto {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editorial;
    private Integer stock;
}
