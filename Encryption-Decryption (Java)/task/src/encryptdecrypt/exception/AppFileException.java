package encryptdecrypt.exception;

public class AppFileException extends RuntimeException {
    public AppFileException(String message) {
        super(message);
    }

    public AppFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
