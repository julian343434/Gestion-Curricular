package com.example.demo.modelo.usuarios.entidades;

import jakarta.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "presenta") // Define la tabla de base de datos asociada a esta entidad
public class PresentaEntidad {

    @EmbeddedId
    @JsonIgnore // Evita que este campo se serialice en respuestas JSON
    private PresentaIdEntidad id; // Clave compuesta que representa las relaciones

    @JsonIgnore
    @ManyToOne
    @MapsId("id_usuario") // Relación que usa id_usuario como parte de la clave compuesta
    @JoinColumn(name = "id_usuario") // Especifica la columna de unión en la base de datos
    private UsuarioEntidad usuario;

    @ManyToOne
    @MapsId("id_rol") // Relación que usa id_rol como parte de la clave compuesta
    @JoinColumn(name = "id_rol") // Especifica la columna de unión en la base de datos
    private RolEntidad rol;

    public PresentaEntidad() {}

    // Métodos getter y setter
    public PresentaIdEntidad getId() { return id; }
    public void setId(PresentaIdEntidad id) { this.id = id; }

    public int getAnio() { return id.getAnio(); } // Obtiene el año de la clave compuesta
    public void setAnio(int anio) { this.id.setAnio(anio); }

    public int getPeriodo() { return id.getPeriodo(); } // Obtiene el periodo de la clave compuesta
    public void setPeriodo(int periodo) { this.id.setPeriodo(periodo); }

    public UsuarioEntidad getUsuario() { return usuario; } // Usuario asociado
    public void setUsuario(UsuarioEntidad usuario) { this.usuario = usuario; }

    public RolEntidad getRol() { return rol; } // Rol asociado
    public void setRol(RolEntidad rol) { this.rol = rol; }
}
