package com.example.demo.modelo.PlanEstudio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.PlanEstudio.entidades.PlanEstudioEntidad;
import com.example.demo.modelo.PlanEstudio.repositorios.PlanEstudioRepositorio;

@Service
public class PlanEstudioServicio {

    @Autowired
    PlanEstudioRepositorio planEstudioRepositorio;


    public List<PlanEstudioEntidad> ObtenerPlanEstudio(){
          List<PlanEstudioEntidad> planEstudio = planEstudioRepositorio.findAll();
        if(planEstudio.isEmpty()){
            throw new RuntimeException("Sin planEstudio registrados");
        }
        return planEstudio;
    }

    public PlanEstudioEntidad buscarId(Long id){
        return planEstudioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("plan de estudios no encontrado"));
    }

    public PlanEstudioEntidad guardarPlanEstudio(Map<String, Object> datos){
        if (!(datos.get("archivo") instanceof byte[])) {
            throw new RuntimeException("El valor de 'archivo' no es del tipo esperado byte[]." + datos.get("archivo").getClass().getName());
        }

        return planEstudioRepositorio.save(new PlanEstudioEntidad((byte[]) datos.get("archivo"),
                                                                    (String) datos.get("descripcion"),
                                                                    (String) datos.get("nombre"),
                                                                    new ArrayList<>()));
    }

    public void eliminarPlanEstudio(Long id){
        if (!planEstudioRepositorio.existsById(id)) {
            throw new RuntimeException("El Plan de Estudio con ID " + id + " no existe.");
        }

        planEstudioRepositorio.deleteById(id);
    }
    
    
}