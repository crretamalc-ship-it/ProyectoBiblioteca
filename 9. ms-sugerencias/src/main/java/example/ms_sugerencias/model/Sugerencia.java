package example.ms_sugerencias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Entidad Sugerencia")
@Entity
@Table(name = "sugerencias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sugerencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la sugerencia", example = "20")
    private Long id;

    @Column(nullable = false, length = 200)
    @Schema(description = "Titulo del libro sugerido", example = "El principito")
    private String titulo;

    @Column(nullable = false, length = 120)
    @Schema(description = "Autor del libro sugerido", example = "Antoine de Saint-Exupery")
    private String autor;

    @Column(unique = true, length = 20)
    @Schema(description = "ISBN del libro sugerido", example = "9780156012195")
    private String isbn;

    @Column(length = 500)
    @Schema(description = "Comentario del socio sobre la sugerencia", example = "Seria bueno tener este clasico en la biblioteca")
    private String comentario;

    /** PENDIENTE, APROBADA, RECHAZADA, COMPRADA */
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado de revision de la sugerencia", example = "PENDIENTE")
    private String estado;

    @Column(name = "fecha_sugerencia")
    @Schema(description = "Fecha y hora de creacion de la sugerencia", example = "2026-06-29T14:30:00")
    private LocalDateTime fechaSugerencia;

    /** Email del socio (validado contra user-service). */
    @Column(name = "email_socio", nullable = false)
    @Schema(description = "Correo del socio que realiza la sugerencia", example = "ana.perez@biblioteca.cl")
    private String emailSocio;
}
