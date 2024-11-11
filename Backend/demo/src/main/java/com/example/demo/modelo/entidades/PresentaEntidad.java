package com.example.demo.modelo.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "presenta")
public class PresentaEntidad {

    @EmbeddedId
    private PresentaIdEntidad id;

    @ManyToOne
    @MapsId("id_usuario")
    @JoinColumn(name = "id_usuario")
    private UsuarioEntidad usuario;

    @ManyToOne
    @MapsId("id_rol")
    @JoinColumn(name = "id_rol")
    private RolEntidad rol;

    public int getAnio() { return id.getAnio(); }
    public void setAnio(int anio) { this.id.setAnio(anio); }

    public int getPeriodo() { return id.getPeriodo(); }
    public void setPeriodo(int periodo) { this.id.setPeriodo(periodo); }

    public UsuarioEntidad getUsuario() { return usuario; }
    public void setUsuario(UsuarioEntidad usuario) { this.usuario = usuario; }

    public RolEntidad getRol() { return rol; }
    public void setRol(RolEntidad rol) { this.rol = rol; }

}
