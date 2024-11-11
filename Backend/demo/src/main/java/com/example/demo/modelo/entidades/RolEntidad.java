package com.example.demo.modelo.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "rol") 
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;
    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<PresentaEntidad> usuarios;

    public RolEntidad() {}
    public RolEntidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void serNombre(String nombre) { this.nombre = nombre; }

    public List<PresentaEntidad> getUsuarios() { return usuarios; }
    public void setUsuarios(List<PresentaEntidad> usuarios) { this.usuarios = usuarios; }

}
