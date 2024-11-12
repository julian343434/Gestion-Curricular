package com.example.demo.modelo.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "rol") 
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "rol")
    private List<PresentaEntidad> usuarios;

    public RolEntidad() {}
    public RolEntidad(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id_rol; }
    public void serId(Long id_rol) { this.id_rol = id_rol; }

    public String getNombre() { return nombre; }
    public void serNombre(String nombre) { this.nombre = nombre; }

    public List<PresentaEntidad> getUsuarios() { return usuarios; }
    public void setUsuarios(List<PresentaEntidad> usuarios) { this.usuarios = usuarios; }

}
