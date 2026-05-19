package com.biblioteca.msvaloraciones.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "valoraciones",
       uniqueConstraints = @UniqueConstraint(columnNames = {"libro_id", "email_usuario"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Id del libro (validado contra ms-inventario via Feign). */
    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    /** Email del autor de la resenia (validado contra user-service via Feign). */
    @Column(name = "email_usuario", nullable = false)
    private String emailUsuario;

    /** Puntuacion 1..5 */
    @Column(nullable = false)
    private Integer puntuacion;

    @Column(nullable = false, length = 500)
    private String comentario;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
}
