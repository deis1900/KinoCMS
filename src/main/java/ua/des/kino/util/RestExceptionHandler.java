package ua.des.kino.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.des.kino.util.exception_handler.session.SessionIdMismatchException;
import ua.des.kino.util.exception_handler.session.NoSuchElementFoundException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoSuchElementFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Customer not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({SessionIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}

