package proyectoModulo_kennet_20240438.kenneh_2040438.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LIBROS")
@Getter @Setter @ToString
@EqualsAndHashCode
public class LibroEntity {

    @Id
    @Column(name = "ID_LIBROS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libros_sequence")
    @SequenceGenerator(name = "libros_sequence",sequenceName = "libros_sequence",allocationSize = 1)
    private Long id_libros;

    @Column(name = "TITULO", unique = true)
    private String titulo;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "ANIO_PUBLICADO")
    private int anio_publicacion;

    @Column(name = "GENERO")
    private int genero;

    @Column(name = "ID")
    private Long id;
}
