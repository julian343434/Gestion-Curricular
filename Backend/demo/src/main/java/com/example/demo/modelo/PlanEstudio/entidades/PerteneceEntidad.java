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
    @JoinColumn(name = "id_curso")
    private CursoEntidad curso;

    @ManyToOne
    @MapsId("id_plan_estudio") // Parte de la clave compuesta
    @JoinColumn(name = "id_plan_estudio")
    private PlanEstudioEntidad planEstudio;

    public PerteneceEntidad() {}
    
    public PerteneceIdEntidad getId() { return id; }
    public void setId(PerteneceIdEntidad id) { this.id = id; }

    public CursoEntidad getCurso() { return curso; }
    public void setCurso(CursoEntidad curso) { this.curso = curso; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
}
