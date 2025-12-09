package org.example.tackit.common.aop.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.tackit.global.exception.CustomBaseException;
import org.example.tackit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<ErrorResponse> handleCustomBaseException(CustomBaseException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("CustomBaseException: {}", e.getMessage(), e);

        ErrorResponse response = new ErrorResponse(
                errorCode.getMessage(),
                errorCode.getHttpStatus().value()
        );

        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.error("ResponseStatusException: {}", ex.getReason(), ex);

        ErrorResponse response = new ErrorResponse(
                ex.getReason(),
                ex.getStatusCode().value()
        );

        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        // 모든 처리되지 않은 예외를 catch
        log.error("Unhandled Exception: {}", ex.getMessage(), ex);

        final ErrorCode serverError = ErrorCode.INTERNAL_SERVER_ERROR;

        ErrorResponse response = new ErrorResponse(
                serverError.getMessage(), // String (메시지)
                serverError.getHttpStatus().value() // int (상태 코드 값)
        );

        return new ResponseEntity<>(response, serverError.getHttpStatus());
    }
}
