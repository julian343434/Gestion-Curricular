package com.example.demo.modelo.usuarios.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "rol") // Tabla asociada para la entidad de roles
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria generada automáticamente
    private Long id_rol; // Identificador del rol

    @Column(length = 18) // Restricción de longitud para la columna en la base de datos
    private String nombre; // Nombre del rol

    @JsonIgnore
    @OneToMany(mappedBy = "rol") // Relación uno a muchos con PresentaEntidad
    private List<PresentaEntidad> usuarios; // Lista de usuarios asociados al rol

    public RolEntidad() {}
    public RolEntidad(String nombre) {
        this.nombre = nombre;
    }

    // Métodos getter y setter
    public Long getId() { return id_rol; }
    public void serId(Long id_rol) { this.id_rol = id_rol; }

    public String getNombre() { return nombre; }
    public void serNombre(String nombre) { this.nombre = nombre; }

    public List<PresentaEntidad> getUsuarios() { return usuarios; }
    public void setUsuarios(List<PresentaEntidad> usuarios) { this.usuarios = usuarios; }
}
