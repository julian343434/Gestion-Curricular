package com.example.demo.controlador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Usuario")
public class Usuario {

    @GetMapping("/saludo")
    public String saludo() {
        return "¡Spring Boot está funcionando hola como estas!";
    }
}
