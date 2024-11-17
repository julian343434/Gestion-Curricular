package com.example.demo.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.entidades.PresentaEntidad;
import com.example.demo.modelo.repositorios.PresentaRepositorio;
import com.example.demo.modelo.repositorios.UsuarioRepositorio;
import com.example.demo.util.JwtUtil;

@Service
public class AutenticacionServicio {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PresentaRepositorio presentaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public String login(String nombreUsuario, String contrasena){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nombreUsuario, contrasena));

        if(authentication.isAuthenticated()){
            Long id =  usuarioRepositorio.findByNombreUsuario(nombreUsuario).orElseThrow(() -> new RuntimeException("usario no encontrado")).getId();
            PresentaEntidad presentaEntidad = presentaRepositorio.findFirstByUsuarioIdOrderByIdAnioDescIdPeriodoDesc(id).orElseThrow(() -> new RuntimeException("ultimo rol no encontrado"));
            return jwtUtil.generarToken(nombreUsuario, presentaEntidad.getRol().getNombre());
        }else{
            throw new RuntimeException("credenciales invalidas");
        }
    }

}
