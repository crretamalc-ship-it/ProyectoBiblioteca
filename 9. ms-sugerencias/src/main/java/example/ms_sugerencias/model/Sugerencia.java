package example.ms_sugerencias.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sugerencias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sugerencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 120)
    private String autor;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 500)
    private String comentario;

    /** PENDIENTE, APROBADA, RECHAZADA, COMPRADA */
    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_sugerencia")
    private LocalDateTime fechaSugerencia;

    /** Email del socio (validado contra user-service). */
    @Column(name = "email_socio", nullable = false)
    private String emailSocio;
}
