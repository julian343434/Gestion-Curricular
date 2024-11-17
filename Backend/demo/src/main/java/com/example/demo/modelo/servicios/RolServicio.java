package com.example.demo.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.entidades.RolEntidad;
import com.example.demo.modelo.repositorios.RolRepositorio;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    public RolEntidad obtenerRol(String nombre){
        return rolRepositorio.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Rol " + nombre + " no encontrado"));
    }

}
