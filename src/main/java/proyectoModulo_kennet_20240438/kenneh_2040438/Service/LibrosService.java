package proyectoModulo_kennet_20240438.kenneh_2040438.Service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyectoModulo_kennet_20240438.kenneh_2040438.Entities.LibroEntity;
import proyectoModulo_kennet_20240438.kenneh_2040438.Exceptions.ExceptionLibrosNoEncontrado;
import proyectoModulo_kennet_20240438.kenneh_2040438.Exceptions.ExceptionLibrosNoRegistrado;
import proyectoModulo_kennet_20240438.kenneh_2040438.Model.DTO.LibroDTO;
import proyectoModulo_kennet_20240438.kenneh_2040438.Repositories.LibroRepositories;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibrosService {

    @Autowired
    LibroRepositories repo;

    public List<LibroDTO> ObtenerLibros() {
        List<LibroEntity> Libro = repo.findAll();

        return Libro.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }


    public LibroDTO InsertarLibros(LibroDTO json) {
        try{
            LibroEntity entity = convertirAEntiry(json);
            LibroEntity Libroguardado = repo.save(entity);
            return convertirADTO(Libroguardado);
        }catch (Exception e){
            log.error("Error al registrar" + e.getMessage());
            throw new ExceptionLibrosNoRegistrado("Error el libro no pudo ser registrado");
        }
    }

    private LibroEntity convertirAEntiry(LibroDTO json) {
        LibroEntity ENTITY = new LibroEntity();
        ENTITY.setId(json.getId());
        ENTITY.setTitulo(json.getTitulo());
        ENTITY.setIsbn(json.getIsbn());
        ENTITY.setAnio_publicacion(json.getAnio_publicacion());
        ENTITY.setGenero(json.getGenero());
        ENTITY.setAutor_id(json.getAutor_id());
        return ENTITY;
    }

    private Object convertirADTO(LibroEntity libroEntity) {
        LibroDTO dto = new LibroDTO();

        dto.setId(libroEntity.getId());
        dto.setTitulo(libroEntity.getTitulo());
        dto.setIsbn(libroEntity.getIsbn());
        dto.setAnio_publicacion(libroEntity.getAnio_publicacion());
        dto.setGenero(libroEntity.getGenero());
        dto.setAutor_id(libroEntity.getAutor_id());

        return dto;
    }

    public boolean eliminarLibros(Long id) {
    }

    public LibroDTO ActualizarLibros(Long id, @Valid LibroDTO json) {
    }
}
