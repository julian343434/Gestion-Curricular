package com.example.demo.modelo.PlanEstudio.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "pertenece") // Tabla que conecta cursos con planes de estudio
public class PerteneceEntidad {

    @EmbeddedId // Clave compuesta
    @JsonIgnore
    private PerteneceIdEntidad id;

    private int anio; // Año en que el curso pertenece al plan

    @JsonIgnore
    @ManyToOne
    @MapsId("id_curso") // Parte de la clave compuesta
    @JoinColumn(name = "id_curso", nullable = false)
    private CursoEntidad curso;

    @ManyToOne
    @MapsId("id_plan_estudio") // Parte de la clave compuesta
    @JoinColumn(name = "id_plan_estudio", nullable = false)
    private PlanEstudioEntidad planEstudio;

    public PerteneceEntidad() {}
    public PerteneceEntidad(PerteneceIdEntidad id, int anio) {
        this.id = id;
        this.anio = anio;
    }
    
    public PerteneceIdEntidad getId() { return id; }
    public void setId(PerteneceIdEntidad id) { this.id = id; }

    public CursoEntidad getCurso() { return curso; }
    public void setCurso(CursoEntidad curso) { this.curso = curso; }

    public PlanEstudioEntidad getPlanEstudio() { return planEstudio; }
    public void setPlanEstudio(PlanEstudioEntidad planEstudio) { this.planEstudio = planEstudio; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
}
