package proyectoModulo_kennet_20240438.kenneh_2040438.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
public class LibroDTO {
    private Long id_libros;

    @NotBlank
    private String titulo;

    @NotBlank
    private String isbn;

    @NotNull
    private int anio_publicado;

    @NotNull
    private int genero;

    @NotNull
    private int id;
}
