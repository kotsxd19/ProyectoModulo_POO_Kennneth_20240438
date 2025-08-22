package proyectoModulo_kennet_20240438.kenneh_2040438.Service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private LibroRepositories repo;

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
        ENTITY.setId_libros(json.getId_libros());
        ENTITY.setTitulo(json.getTitulo());
        ENTITY.setIsbn(json.getIsbn());
        ENTITY.setAnio_publicado(json.getAnio_publicado());
        ENTITY.setGenero(json.getGenero());
        ENTITY.setId(json.getId());
        return ENTITY;
    }

    private LibroDTO convertirADTO(LibroEntity enty) {
        LibroDTO dto = new LibroDTO();

        dto.setId_libros(enty.getId_libros());

        dto.setTitulo(enty.getTitulo());

        dto.setIsbn(enty.getIsbn());

        dto.setAnio_publicado(enty.getAnio_publicado());

        dto.setGenero(enty.getGenero());

        dto.setId(enty.getId());


        return dto;
    }

    public boolean eliminarLibros(Long id) {
        try {
            LibroEntity existe = repo.findById(id).orElse(null);
            if (existe != null){
                repo.deleteById(id);
                return true;
            }else {
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el ID:" + id + "Para eliminar", 1);
        }
    }

    public LibroDTO ActualizarLibros(Long id, @Valid LibroDTO json) {
        LibroEntity existente = repo.findById(id)
                .orElseThrow(() -> new ExceptionLibrosNoEncontrado("no encontrado"));

        existente.setTitulo(json.getTitulo());
        existente.setIsbn(json.getIsbn());
        existente.setAnio_publicado(json.getAnio_publicado());
        existente.setGenero(json.getGenero());
        existente.setId(json.getId());
        LibroEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }
}
