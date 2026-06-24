package com.example.assignment.Utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument( IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", 400, "error", ex.getMessage()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateError(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", 400, "error", "Invalid date format. Use yyyy-MM-ddTHH:mm:ss"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class
    )
    public ResponseEntity<?> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", 400, "error", ex.getParameterName() + " is required"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("status", 500, "error", "Internal Server Error"));
    }
}