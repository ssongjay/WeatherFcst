package com.example.weatherforecast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        String errorMessage = e.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(errorMessage);
    }
}
