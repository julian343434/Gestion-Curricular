package com.example.demo.modelo.PlanEstudio.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.PlanEstudio.entidades.PerteneceEntidad;
import com.example.demo.modelo.PlanEstudio.entidades.PerteneceIdEntidad;
import com.example.demo.modelo.PlanEstudio.repositorios.PerteneceRepositorio;

@Service
public class PerteneceServicio {

    @Autowired
    PerteneceRepositorio perteneceRepositorio;

    public List<PerteneceEntidad> crearRelacionArchivo(Long id_plan_estudio, List<Long> id_curso, int anio){

        List<PerteneceEntidad> relacion = new ArrayList<>();

        for (Long curso : id_curso) {
            relacion.add(perteneceRepositorio.save(new PerteneceEntidad(new PerteneceIdEntidad(id_plan_estudio, curso), anio)));
        }

        return relacion;
    }

    public void guardarRelacion(List<PerteneceEntidad> relaciones){
        for (PerteneceEntidad perteneceEntidad : relaciones) {
            perteneceRepositorio.save(perteneceEntidad);
        }
    }

}
