package com.example.demo.modelo;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PresentaIdEntidad implements Serializable {
    
    private Long id_rol;
    private Long id_usuario;
    private int periodo;
    private int anio;

    public PresentaIdEntidad() {}
    public PresentaIdEntidad(Long id_rol, Long id_usuario,  int periodo, int anio) {
        this.id_rol =  id_rol;
        this.id_usuario =  id_usuario;
        this.periodo = periodo;
        this.anio = anio;
    }

    public Long getId_rol() { return id_rol; }
    public void setId_rol(Long id_rol) { this.id_rol = id_rol; }

    public Long getId_usuario() { return id_usuario; }
    public void setId_usuario(Long id_usuario) { this.id_usuario = id_usuario; }

    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    // hashCode y equals (requeridos para que JPA maneje la clave compuesta)
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
