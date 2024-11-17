package com.example.demo.modelo.servicios;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.entidades.PresentaEntidad;
import com.example.demo.modelo.entidades.PresentaIdEntidad;
import com.example.demo.modelo.entidades.RolEntidad;
import com.example.demo.modelo.repositorios.PresentaRepositorio;

@Service
public class PresentaServicio {

    @Autowired
    private PresentaRepositorio presentaRepositorio;

    public PresentaEntidad crearRelacion(Long id_usuario, Long id_rol, Map<String, Object> datos){

        PresentaEntidad presentaEntidad = new PresentaEntidad();

        presentaEntidad.setId(new PresentaIdEntidad(id_rol , id_usuario, (int) datos.get("periodo"), (int) datos.get("anio")));

        return presentaEntidad;
    }

    public PresentaEntidad guardarRelacion(PresentaEntidad relacion){
        return presentaRepositorio.save(relacion);
    }

    public RolEntidad obtenerUltimoRol(Long usuarioId) {
        return presentaRepositorio.findFirstByUsuarioIdOrderByIdAnioDescIdPeriodoDesc(usuarioId)
                .map(PresentaEntidad::getRol) // Obtiene la entidad de rol desde PresentaEntidad
                .orElseThrow(() -> new RuntimeException("No se encontró ningún rol para el usuario con ID: " + usuarioId));
    }
}
