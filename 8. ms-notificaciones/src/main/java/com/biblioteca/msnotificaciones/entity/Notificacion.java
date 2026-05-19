package com.biblioteca.msnotificaciones.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Email del destinatario (validado contra user-service via Feign). */
    @Column(nullable = false)
    private String emailUsuario;

    @Column(nullable = false, length = 500)
    private String mensaje;

    /**
     * Tipo de notificacion:
     *   VENCIMIENTO_PROXIMO, MULTA_GENERADA, RESERVA_DISPONIBLE, GENERICO
     */
    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    /** PENDIENTE o ENVIADA (simula el envio real de correo). */
    @Column(nullable = false, length = 20)
    private String estado;

    /** Id opcional del recurso relacionado (prestamo o multa). */
    @Column(name = "recurso_id")
    private Long recursoId;
}
