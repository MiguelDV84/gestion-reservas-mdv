package com.example.reservasBoscoMdv.errors;

import com.example.reservasBoscoMdv.enums.ErrorType;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();

        if(cause instanceof ConstraintViolationException cve) {
            String constraintName = cve.getConstraintName();

            if(ErrorType.UNIQUE_TRAMO.getCode().equals(constraintName)) {
                ErrorResponse errorResponse =  ErrorResponse.builder()
                        .error(ErrorType.TRAMO_DUPLICADO.getCode())
                        .message("El tramo horario ya existe para el día especificado.")
                        .detail("No se pueden crear tramos horarios duplicados para el mismo día.")
                        .build();

                return ResponseEntity.badRequest().body(errorResponse);
            }
        }

        ErrorResponse genericError = ErrorResponse.builder()
                .error(ErrorType.DATA_INTEGRITY_VIOLATION.getCode())
                .message("Se ha producido un error de integridad de datos.")
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(genericError);
    }
}
