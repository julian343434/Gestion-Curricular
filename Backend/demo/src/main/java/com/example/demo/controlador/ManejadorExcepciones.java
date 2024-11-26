package com.example.demo.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Permite manejar excepciones globalmente en todos los controladores
public class ManejadorExcepciones {

    /**
     * Maneja excepciones de tipo RuntimeException.
     *
     * @param e La excepción capturada.
     * @return Respuesta con mensaje de error y estado HTTP 400.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntimeExcepcion(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    /**
     * Maneja excepciones genéricas no previstas.
     *
     * @param e La excepción capturada.
     * @return Respuesta con mensaje de error y estado HTTP 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcionGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error inesperado: " + e.getMessage());
    }
}
