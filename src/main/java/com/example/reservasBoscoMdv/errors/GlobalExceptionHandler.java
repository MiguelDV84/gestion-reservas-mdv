package com.example.reservasBoscoMdv.errors;

import com.example.reservasBoscoMdv.enums.ErrorType;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();
        String errores =  ex.getMostSpecificCause().getMessage();

        if(cause instanceof ConstraintViolationException cve) {
            String constraintName = cve.getConstraintName();

            if(ErrorType.UNIQUE_TRAMO.getCode().equals(constraintName)) {
                ErrorResponse errorResponse =  ErrorResponse.builder()
                        .error(ErrorType.TRAMO_DUPLICADO.getCode())
                        .message(ErrorType.TRAMO_DUPLICADO.getMessage())
                        .detail(errores)
                        .build();

                return ResponseEntity.badRequest().body(errorResponse);
            }
        }

        ErrorResponse genericError = ErrorResponse.builder()
                .error(ErrorType.DATA_INTEGRITY_VIOLATION.getCode())
                .message(ErrorType.DATA_INTEGRITY_VIOLATION.getMessage())
                .detail(errores)
                .build();

        return ResponseEntity.badRequest().body(genericError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map( e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (a, b) -> a + "; " + b);

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .error(ErrorType.VALIDATION_ERROR.getCode())
                        .message(ErrorType.VALIDATION_ERROR.getMessage())
                        .detail(errores)
                        .build()
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(ex.getCode())
                .message(ex.getMessage())
                .detail("Se ha violado una regla de negocio")
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonError(HttpMessageNotReadableException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .error(ErrorType.JSON_PARSE_ERROR.getCode())
                .message(ErrorType.JSON_PARSE_ERROR.getMessage())
                .detail(ex.getMostSpecificCause().getMessage())
                .build();
        return ResponseEntity.badRequest().body(error);
    }
}
