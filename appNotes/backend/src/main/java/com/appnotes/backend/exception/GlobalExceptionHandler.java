package com.appnotes.backend.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_KEY = "error";
    private static final String MESSAGE_KEY = "message";

    @ExceptionHandler(NotasNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotasNotFound(NotasNotFoundException ex) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put(ERROR_KEY, "NOTA_NO_ENCONTRADA");
        body.put(MESSAGE_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_KEY, "VALIDATION_ERROR");
        body.put(
                "messages",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .toList());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put(ERROR_KEY, "INTERNAL_SERVER_ERROR");
        body.put(MESSAGE_KEY, "Ha ocurrido un error inesperado");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}