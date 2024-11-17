package com.example.demo.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.entidades.UsuarioEntidad;
import com.example.demo.modelo.repositorios.UsuarioRepositorio;

@Service
public class UsuarioAutenticacionServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PresentaServicio presentaServicio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntidad usuario = usuarioRepositorio.findByNombreUsuario(username).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));

        if(!usuario.getActivo()){
            throw new RuntimeException("El usuario está inactivo. Contacte al administrador.");
        }

        return User.builder()
                .username(usuario.getnombreUsuario())
                .password(usuario.getContrasena())
                .roles(presentaServicio.obtenerUltimoRol(usuario.getId()).getNombre())
                .build();
    }

}
