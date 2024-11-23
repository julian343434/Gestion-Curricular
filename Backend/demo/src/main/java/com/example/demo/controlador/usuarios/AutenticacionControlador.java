package com.example.demo.controlador.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.modelo.usuarios.servicios.AutenticacionServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController // Define la clase como un controlador REST gestionado por Spring
@RequestMapping("/autenticar") // Asigna el prefijo de ruta para las solicitudes
public class AutenticacionControlador {

    @Autowired
    private AutenticacionServicio autenticacionServicio; // Servicio de autenticación para manejar lógica de negocio

    /**
     * Punto final para autenticar usuarios.
     * 
     * @param credenciales Objeto que contiene usuario y contraseña.
     * @return Token JWT generado si las credenciales son válidas.
     */
    @PostMapping("/login")
    public String login(@RequestBody Autenticacion credenciales) {
        return autenticacionServicio.login(credenciales.getUsuario(), credenciales.getContrasena());
    }

}

// Clase auxiliar para modelar las credenciales enviadas en el cuerpo de la solicitud
class Autenticacion {

    private String usuario; // Nombre de usuario
    private String contrasena; // Contraseña

    // Constructores
    Autenticacion() {}
    Autenticacion(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // Métodos getter y setter
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
