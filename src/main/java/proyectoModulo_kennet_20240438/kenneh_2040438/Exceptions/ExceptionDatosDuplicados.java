package proyectoModulo_kennet_20240438.kenneh_2040438.Exceptions;

import lombok.Getter;


public class ExceptionDatosDuplicados extends RuntimeException {
    public ExceptionDatosDuplicados(String message) {
        super(message);
    }

    @Getter
    private String duplicado;

    public ExceptionDatosDuplicados(String message, String duplicado){
        super(message);
        this.duplicado = duplicado;
    }
}
