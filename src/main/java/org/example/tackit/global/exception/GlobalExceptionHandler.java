package org.example.tackit.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handle(ResponseStatusException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getReason(), ex.getStatusCode().value()
        );
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}
