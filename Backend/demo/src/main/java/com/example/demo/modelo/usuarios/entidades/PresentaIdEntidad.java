package com.example.demo.modelo.usuarios.entidades;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable // Indica que esta clase se usará como clave embebida en otra entidad
public class PresentaIdEntidad implements Serializable {
    
    private Long id_rol; // Identificador del rol
    @JsonIgnore
    private Long id_usuario; // Identificador del usuario
    private int periodo; // Periodo asociado
    private int anio; // Año asociado

    public PresentaIdEntidad() {}

    public PresentaIdEntidad(Long id_rol, Long id_usuario, int periodo, int anio) {
        this.id_rol = id_rol;
        this.id_usuario = id_usuario;
        this.periodo = periodo;
        this.anio = anio;
    }

    // Métodos getter y setter
    public Long getId_rol() { return id_rol; }
    public void setId_rol(Long id_rol) { this.id_rol = id_rol; }

    public Long getId_usuario() { return id_usuario; }
    public void setId_usuario(Long id_usuario) { this.id_usuario = id_usuario; }

    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    // Métodos hashCode y equals para manejar la clave compuesta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PresentaIdEntidad that = (PresentaIdEntidad) o;

        if (periodo != that.periodo) return false;
        if (anio != that.anio) return false;
        if (!id_usuario.equals(that.id_usuario)) return false;
        return id_rol.equals(that.id_rol);
    }

    @Override
    public int hashCode() {
        int result = id_usuario.hashCode();
        result = 31 * result + id_rol.hashCode();
        result = 31 * result + periodo;
        result = 31 * result + anio;
        return result;
    }
}
