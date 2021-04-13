package ua.des.kino.util.exception_handler;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
