package com.biblioteca.msvaloraciones.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Entidad Valoracion")
@Entity
@Table(name = "valoraciones",
       uniqueConstraints = @UniqueConstraint(columnNames = {"libro_id", "email_usuario"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la valoracion", example = "9")
    private Long id;

    /** Id del libro (validado contra ms-inventario via Feign). */
    @Column(name = "libro_id", nullable = false)
    @Schema(description = "ID del libro valorado", example = "10")
    private Long libroId;

    /** Email del autor de la resenia (validado contra user-service via Feign). */
    @Column(name = "email_usuario", nullable = false)
    @Schema(description = "Correo del usuario que realiza la valoracion", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    /** Puntuacion 1..5 */
    @Column(nullable = false)
    @Schema(description = "Puntuacion asignada al libro", example = "5")
    private Integer puntuacion;

    @Column(nullable = false, length = 500)
    @Schema(description = "Comentario de la valoracion", example = "Excelente libro, muy recomendado")
    private String comentario;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha y hora de creacion de la valoracion", example = "2026-06-29T14:30:00")
    private LocalDateTime fechaCreacion;
}
