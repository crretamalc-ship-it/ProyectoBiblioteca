package com.biblioteca.ms_inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Schema(description = "Entidad Libro")
@Entity
@Data
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del libro", example = "10")
    private Long id;
    
    @Schema(description = "Titulo del libro", example = "Cien anios de soledad")
    private String titulo;

    @Schema(description = "Autor del libro", example = "Gabriel Garcia Marquez")
    private String autor;

    @Column(unique = true)
    @Schema(description = "Codigo ISBN del libro", example = "9780307474728")
    private String isbn;

    @Schema(description = "Editorial del libro", example = "Sudamericana")
    private String editorial;

    @Schema(description = "Cantidad de ejemplares disponibles", example = "5")
    private Integer stock;
}
