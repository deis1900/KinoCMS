package ua.des.kino.util.exception_handler;

public class SessionIdMismatchException extends RuntimeException{
    public SessionIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
