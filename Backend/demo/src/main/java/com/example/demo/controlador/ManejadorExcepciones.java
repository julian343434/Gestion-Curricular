package com.example.demo.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntimeExcepcion(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

     // Manejar excepciones no previstas (genéricas)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcionGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error inesperado: " + e.getMessage());
    }
} 