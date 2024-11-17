package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.servicios.AutenticacionServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/autenticar")
public class AutenticacionControlador {

    @Autowired
    private AutenticacionServicio autenticacionServicio;

    @PostMapping("/login")
    public String login(@RequestBody Autenticacion credenciales) {
        return autenticacionServicio.login(credenciales.getUsuario(), credenciales.getContrasena());
    }

}

class Autenticacion{

    private String usuario;
    private String contrasena;

    Autenticacion() {}
    Autenticacion(String usuario, String contrasena) { this.usuario = usuario; this.contrasena = contrasena;}

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

}