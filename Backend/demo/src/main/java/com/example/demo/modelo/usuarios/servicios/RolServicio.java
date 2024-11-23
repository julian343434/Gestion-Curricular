package com.example.demo.modelo.usuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.modelo.usuarios.entidades.RolEntidad;
import com.example.demo.modelo.usuarios.repositorios.RolRepositorio;

@Service // Define la clase como un servicio gestionado por Spring
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    // Busca un rol por su nombre
    public RolEntidad obtenerRol(String nombre){
        return rolRepositorio.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Rol " + nombre + " no encontrado"));
    }
}
