package proyectoModulo_kennet_20240438.kenneh_2040438.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proyectoModulo_kennet_20240438.kenneh_2040438.Entities.LibroEntity;
import proyectoModulo_kennet_20240438.kenneh_2040438.Exceptions.ExceptionDatosDuplicados;
import proyectoModulo_kennet_20240438.kenneh_2040438.Exceptions.ExceptionLibrosNoEncontrado;
import proyectoModulo_kennet_20240438.kenneh_2040438.Model.DTO.LibroDTO;
import proyectoModulo_kennet_20240438.kenneh_2040438.Service.LibrosService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/LibrosApi")
public class LibrosController {

    @Autowired
    LibrosService service;

    @GetMapping("/getLibros")
    public List<LibroDTO> obtenerdatos(){
        return service.ObtenerLibros();
    }

    @PostMapping("/PutLibros")
    public ResponseEntity<?> nuevosdatos(@Valid @RequestBody LibroDTO json
    ){
        try{
            LibroDTO respuesta = service.InsertarLibros(json);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserccion fallida",
                        "errorType", "VALIDACION_ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                        "status", "Succes",
                        "data", respuesta
                ));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al registrar usuario",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("PutLirbos")
    public  ResponseEntity<?> modificar(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String>errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errores);
        }

        try{
            LibroDTO dto = service.ActualizarLibros(id, json);
            return  ResponseEntity.ok(dto);
        }catch (ExceptionLibrosNoEncontrado e){
            return ResponseEntity.notFound().build();
        }catch (ExceptionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                      "Error", "Datos duplicados",
                    "Campo", e.getDuplicado()
                    )
            );
        }
    }

    @DeleteMapping("/DeleteLibros/{id}")
    public ResponseEntity<?> eliminarLibros(
            @PathVariable Long id
    ){
        try{
            if (!service.eliminarLibros(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje de error", "Usuario no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mesaje", "El usuario no fue encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Succes",
                    "message", "Usuario eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.ok().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar usuario",
                    "detail", e.getMessage()
            ));
        }
    }

}
