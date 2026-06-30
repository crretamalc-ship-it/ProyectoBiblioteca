package com.biblioteca.msnotificaciones.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Entidad Notificacion")
@Entity
@Table(name = "notificaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la notificacion", example = "12")
    private Long id;

    /** Email del destinatario (validado contra user-service via Feign). */
    @Column(nullable = false)
    @Schema(description = "Correo del usuario destinatario", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    @Column(nullable = false, length = 500)
    @Schema(description = "Mensaje de la notificacion", example = "Tu prestamo vence pronto")
    private String mensaje;

    /**
     * Tipo de notificacion:
     *   VENCIMIENTO_PROXIMO, MULTA_GENERADA, RESERVA_DISPONIBLE, GENERICO
     */
    @Column(nullable = false, length = 30)
    @Schema(description = "Tipo de notificacion", example = "VENCIMIENTO_PROXIMO")
    private String tipo;

    @Column(name = "fecha_envio", nullable = false)
    @Schema(description = "Fecha y hora programada o registrada de envio", example = "2026-07-09T10:00:00")
    private LocalDateTime fechaEnvio;

    /** PENDIENTE o ENVIADA (simula el envio real de correo). */
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado de la notificacion", example = "PENDIENTE")
    private String estado;

    /** Id opcional del recurso relacionado (prestamo o multa). */
    @Column(name = "recurso_id")
    @Schema(description = "ID del recurso relacionado", example = "15")
    private Long recursoId;
}
