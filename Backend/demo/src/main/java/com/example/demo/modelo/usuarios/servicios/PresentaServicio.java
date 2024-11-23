package com.example.demo.modelo.usuarios.servicios;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.modelo.usuarios.entidades.PresentaEntidad;
import com.example.demo.modelo.usuarios.entidades.PresentaIdEntidad;
import com.example.demo.modelo.usuarios.entidades.RolEntidad;
import com.example.demo.modelo.usuarios.repositorios.PresentaRepositorio;

@Service // Define la clase como un servicio gestionado por Spring
public class PresentaServicio {

    @Autowired
    private PresentaRepositorio presentaRepositorio;

    // Crea una nueva relación usuario-rol con datos proporcionados
    public PresentaEntidad crearRelacion(Long id_usuario, Long id_rol, Map<String, Object> datos){
        PresentaEntidad presentaEntidad = new PresentaEntidad();
        presentaEntidad.setId(new PresentaIdEntidad(id_rol , id_usuario, (int) datos.get("periodo"), (int) datos.get("anio")));
        return presentaEntidad;
    }

    // Guarda la relación en la base de datos
    public PresentaEntidad guardarRelacion(PresentaEntidad relacion){
        return presentaRepositorio.save(relacion);
    }

    // Obtiene el rol más reciente asignado a un usuario
    public RolEntidad obtenerUltimoRol(Long usuarioId) {
        return presentaRepositorio.findFirstByUsuarioIdOrderByIdAnioDescIdPeriodoDesc(usuarioId)
                .map(PresentaEntidad::getRol)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún rol para el usuario con ID: " + usuarioId));
    }
}
