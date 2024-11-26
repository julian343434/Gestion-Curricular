package com.example.demo.modelo.PlanEstudio.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "plan_estudio") // Define la tabla de planes de estudio
public class PlanEstudioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plan_estudio;

    @JsonIgnore
    @Column(name = "archivo", columnDefinition = "bytea")
    private byte[] archivo; // Archivo asociado al plan de estudio

    private String descripcion; // Descripción del plan de estudio
    private String nombre; // Nombre del plan de estudio
    
    @JsonIgnore
    @OneToMany(mappedBy = "planEstudio", cascade = CascadeType.ALL) // Relación uno a muchos con cursos
    private List<PerteneceEntidad> cursos;

    public PlanEstudioEntidad() {}
    public PlanEstudioEntidad(byte[] archivo, String descripcion, String nombre, List<PerteneceEntidad> cursos) {
        this.archivo = archivo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.cursos = cursos;
    }

    public Long getId_plan_estudio() { return id_plan_estudio; }
    public void setId_plan_estudio(Long id_plan_estudio) { this.id_plan_estudio = id_plan_estudio; }

    public byte[] getArchivo() { return archivo; }
    public void setArchivo(byte[] archivo) { this.archivo = archivo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<PerteneceEntidad> getCursos() { return cursos; }
    public void setCursos(List<PerteneceEntidad> cursos) { this.cursos = cursos; }

}
