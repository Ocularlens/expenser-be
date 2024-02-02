package org.ocularlens.expenserbe.exception;

import org.ocularlens.expenserbe.custom.util.StringExtractor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        StringBuilder errors = new StringBuilder();

        for (FieldError error : ex.getFieldErrors()) {
            errors.append(error.getDefaultMessage()).append(", ");
        }

        errors = new StringBuilder(errors.substring(0, errors.length() - 2));

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                errors.toString(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request
    ) {
        String message = "Value causing duplicate entry:" + StringExtractor.extractValueFromErrorMessage(
                ex.getMessage()
        );
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
