package com.example.demo.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.entidades.UsuarioEntidad;
import com.example.demo.modelo.repositorios.UsuarioRepositorio;

import java.util.List;

@Service
public class UsuarioServicio {
     
    @Autowired
    private UsuarioRepositorio usuario;

    public List<UsuarioEntidad> obtenerUsuarios(){
        return usuario.findAll();
    }
}
