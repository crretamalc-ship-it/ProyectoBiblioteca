package example.ms_favoritos_listas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "Entidad Lista de Favoritos")
@Entity
@Table(name = "listas_favoritos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaFavoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la lista", example = "7")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la lista", example = "Lecturas de verano")
    private String nombre;

    @Column(length = 255)
    @Schema(description = "Descripcion de la lista", example = "Libros recomendados para vacaciones")
    private String descripcion;

    /** Email del propietario (validado contra user-service via Feign). */
    @Column(nullable = false)
    @Schema(description = "Correo del usuario propietario de la lista", example = "ana.perez@biblioteca.cl")
    private String emailUsuario;

    @Column(nullable = false)
    @Schema(description = "Indica si la lista es publica", example = "true")
    private Boolean publica;
}
