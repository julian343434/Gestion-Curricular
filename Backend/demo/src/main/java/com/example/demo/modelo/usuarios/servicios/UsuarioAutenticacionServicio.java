package com.example.demo.modelo.usuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.modelo.usuarios.entidades.UsuarioEntidad;
import com.example.demo.modelo.usuarios.repositorios.UsuarioRepositorio;

@Service // Marca esta clase como un servicio gestionado por Spring
public class UsuarioAutenticacionServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // Inyección de dependencias para el repositorio de usuarios

    @Autowired
    private PresentaServicio presentaServicio; // Inyección de dependencias para manejar relaciones usuario-rol

    /**
     * Método que carga los detalles de un usuario específico según el nombre de usuario.
     * Este método es requerido para implementar `UserDetailsService` de Spring Security.
     *
     * @param username El nombre de usuario que se busca en el sistema.
     * @return UserDetails con la información del usuario (nombre, contraseña, roles).
     * @throws UsernameNotFoundException Si el usuario no se encuentra o no está activo.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario por su nombre de usuario
        UsuarioEntidad usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));

        // Verifica si el usuario está activo
        if (!usuario.getActivo()) {
            throw new RuntimeException("El usuario está inactivo. Contacte al administrador.");
        }

        // Crea un objeto `UserDetails` con la información del usuario
        return User.builder()
                .username(usuario.getNombreUsuario()) // Establece el nombre de usuario
                .password(usuario.getContrasena())    // Establece la contraseña
                .roles(presentaServicio.obtenerUltimoRol(usuario.getId()).getNombre()) // Establece el rol más reciente
                .build();
    }
}
