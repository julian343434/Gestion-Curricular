package com.example.demo.modelo.PlanEstudio.entidades;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;

@Embeddable
public class PerteneceIdEntidad implements Serializable{

    private Long id_plan_estudio; // ID del plan de estudio
    @JsonIgnore
    private Long id_curso; // ID del curso

    public PerteneceIdEntidad() {}
    public PerteneceIdEntidad(Long id_plan_estudio, Long id_curso) { this.id_plan_estudio = id_plan_estudio;
                                                            this.id_curso = id_curso; }
    
    public Long getId_plan_estudio() { return id_plan_estudio; }
    public void setId_plan_estudio(Long id_plan_estudio) { this.id_plan_estudio = id_plan_estudio; }

    public Long getid_curso() { return id_curso; }
    public void setid_curso(Long id_curso) { this.id_curso = id_curso; }

     // hashCode y equals necesarios para claves compuestas en JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerteneceIdEntidad that = (PerteneceIdEntidad) o;

        if (id_plan_estudio != that.id_plan_estudio) return false;
        return id_curso.equals(that.id_curso);
    }

    @Override
    public int hashCode() {
        int result = id_plan_estudio.hashCode();
        result = 31 * result + id_curso.hashCode();
        return result;
    }

}
