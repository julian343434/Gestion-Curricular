package com.example.demo.modelo.PlanEstudio.entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso") // Define la tabla asociada en la base de datos
public class CursoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    private Long id_curso;

    private int semestre; // Semestre en el que se imparte el curso
    @Column(length = 20)
    private String nombre; // Nombre del curso
    private boolean obligatorio; // Indica si el curso es obligatorio
    private int creditos; // Créditos del curso
    @Column(length = 4)
    private String relacion; // Relación entre cursos (1:01, 1:02)
    @Column(length = 2)
    private String tipo; // Tipo de curso (teórico o teorico practicas)
    @Column(name = "horas_de_trabajo")
    private int horasDeTrabajo; // Horas de trabajo requeridas
    @Column(name = "area_de_formacion", columnDefinition = "varchar(20)")
    private String areaDeFormacion; // Área de formación (Profesional, Profundizacion, Investigacion, complementarias.)
    @Column(name = "max_estudiantes")
    private int maxEstudiantes; // Número máximo de estudiantes permitidos

    @OneToMany(mappedBy = "curso") // Relación uno a muchos con `PerteneceEntidad`
    private List<PerteneceEntidad> planEstudio;

    public CursoEntidad() {}
    public CursoEntidad(Long id_curso, 
                int semestre, 
                String nombre, 
                boolean obligatorio, 
                int creditos, 
                String relacion,
                String tipo,
                int horasDeTrabajo,
                String areaDeFromacion,
                int maxEstudiantes){
                    this.id_curso = id_curso;
                    this.semestre = semestre;
                    this.obligatorio = obligatorio;
                    this.creditos = creditos;
                    this.relacion = relacion;
                    this.tipo = tipo;
                    this.horasDeTrabajo = horasDeTrabajo;
                    this.areaDeFormacion = areaDeFromacion;
                    this.maxEstudiantes = maxEstudiantes;
                }

    
    public Long getId_curso() { return id_curso; }
    public void setId_curso(Long id_curso){ this.id_curso = id_curso; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre){ this.semestre = semestre; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }

    public boolean getObligatorio() { return obligatorio; }
    public void setObligatorio(boolean obligatorio){ this.obligatorio = obligatorio; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos){ this.creditos = creditos; }

    public String getRelacion() { return relacion; }
    public void setRelacion(String relacion){ this.relacion = relacion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo){ this.tipo = tipo; }

    public int getHorasDeTrabajo() { return horasDeTrabajo; }
    public void setHoraDeTrabajo(int horasDeTrabajo){ this.horasDeTrabajo = horasDeTrabajo; }

    public String getAreaDeFormacion() { return areaDeFormacion; }
    public void setAreaDeFormacion(String areaDeFormacion){ this.areaDeFormacion = areaDeFormacion; }

    public int getMaxEstudiantes() { return maxEstudiantes; }
    public void setMaxEstudiantes(int maxEstudiantes){ this.maxEstudiantes = maxEstudiantes; }

    public List<PerteneceEntidad> getplanEstudio() { return planEstudio; }
    public void setplanEstudio(List<PerteneceEntidad> planEstudio) { this.planEstudio = planEstudio; }
}
