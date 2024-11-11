package com.example.demo.controlador;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.modelo.entidades.UsuarioEntidad;
import com.example.demo.modelo.servicios.UsuarioServicio;

@RestController
@RequestMapping("/usuario")
public class Usuario {

    @Autowired
    private UsuarioServicio usuarios;

    @GetMapping("/")
    public List<UsuarioEntidad> obetenerUsuarios() {
        return usuarios.obtenerUsuarios();
    }
}
