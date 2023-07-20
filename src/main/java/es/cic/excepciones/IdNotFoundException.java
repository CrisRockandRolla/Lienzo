package es.cic.excepciones;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException() {
    }

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
