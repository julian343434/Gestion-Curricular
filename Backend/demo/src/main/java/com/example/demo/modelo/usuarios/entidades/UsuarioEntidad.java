package com.example.demo.modelo.usuarios.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "usuario") // Tabla asociada para la entidad de usuarios
public class UsuarioEntidad {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria generada automáticamente
    private Long id_usuario; // Identificador único del usuario
    
    private String nombre; // Nombre completo del usuario

    @Column(name = "nombre_usuario", unique = true, nullable = false, columnDefinition = "varchar(30)") 
    private String nombreUsuario; // Nombre de usuario único para inicio de sesión

    @Column(unique = true, nullable = false)
    private String contrasena; // Contraseña del usuario

    private String correo; // Correo electrónico del usuario
    private boolean activo; // Estado del usuario (activo/inactivo)

    @OneToMany(mappedBy = "usuario") // Relación uno a muchos con PresentaEntidad
    private List<PresentaEntidad> roles; // Lista de roles asociados al usuario

    // Constructor, getters y setters
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

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public boolean getActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<PresentaEntidad> getRol() { return roles; }
    public void setRol(List<PresentaEntidad> roles) { this.roles = roles; }
}
