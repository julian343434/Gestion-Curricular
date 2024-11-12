package com.example.demo.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.entidades.UsuarioEntidad;
import com.example.demo.modelo.repositorios.UsuarioRepositorio;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UsuarioServicio {
     
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
 
    public List<UsuarioEntidad> obtenerUsuarios(){
        return usuarioRepositorio.findAll();
    }

    public UsuarioEntidad guardarUsuario(Map<String, Object> usuario){

        UsuarioEntidad nuevoUsuario = new UsuarioEntidad();

        nuevoUsuario.setNombre((String) usuario.get("nombre"));
        nuevoUsuario.setNombre_usuario((String) usuario.get("nombre_usuario"));
        nuevoUsuario.setCorreo((String) usuario.get("correo"));
        nuevoUsuario.setActivo((boolean) usuario.get("activo"));
        nuevoUsuario.setContrasena(generarHash((String) usuario.get("contrasena"), generarSal()));
        return usuarioRepositorio.save(nuevoUsuario);
    }

      // Método para generar hash de una cadena usando SHA-256
    public static String generarHash(String input, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes()); // Añadir la sal al hash
            byte[] hashedBytes = md.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash", e);
        }
    }
    
    // Método para generar una sal segura
    public static String generarSal() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

}
