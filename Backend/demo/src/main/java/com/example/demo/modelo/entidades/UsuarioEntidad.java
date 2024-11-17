package com.example.demo.modelo.entidades;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "usuario")
public class UsuarioEntidad {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    
    private String nombre;
    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;
    @Column(unique = true, nullable = false)
    private String contrasena;
    private String correo;
    private boolean activo;

    @OneToMany(mappedBy = "usuario")
    private List<PresentaEntidad> roles;

    // Constructor, getters, y setters
    
    public UsuarioEntidad() {}
    
    public UsuarioEntidad(String nombre, String nombre_usuario, String contrasena, String correo, boolean activo) {
        this.nombre = nombre;
        this.nombreUsuario = nombre_usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.activo = activo;
    }

    public Long getId() { return id_usuario; }
    public void setId(Long id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getnombreUsuario() { return nombreUsuario; }
    public void setnombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public boolean getActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<PresentaEntidad> getRol() { return roles; }
    public void setRol(List<PresentaEntidad> roles) { this.roles = roles; }
}

