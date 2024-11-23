package com.example.demo.modelo.usuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.example.demo.modelo.usuarios.entidades.PresentaEntidad;
import com.example.demo.modelo.usuarios.repositorios.PresentaRepositorio;
import com.example.demo.modelo.usuarios.repositorios.UsuarioRepositorio;
import com.example.demo.util.JwtUtil;

@Service // Define la clase como un componente de servicio gestionado por Spring
public class AutenticacionServicio {

    @Autowired
    private AuthenticationManager authenticationManager; // Maneja la autenticación

    @Autowired
    private JwtUtil jwtUtil; // Genera y valida tokens JWT

    @Autowired
    private PresentaRepositorio presentaRepositorio; // Acceso a los datos de relaciones usuario-rol

    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // Acceso a los datos de usuarios

    public String login(String nombreUsuario, String contrasena){
        // Autentica las credenciales del usuario
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nombreUsuario, contrasena));

        if(authentication.isAuthenticated()){
            // Busca el usuario por nombre
            Long id = usuarioRepositorio.findByNombreUsuario(nombreUsuario).orElseThrow(() -> new RuntimeException("usario no encontrado")).getId();
            // Obtiene la relación más reciente entre el usuario y un rol
            PresentaEntidad presentaEntidad = presentaRepositorio.findFirstByUsuarioIdOrderByIdAnioDescIdPeriodoDesc(id).orElseThrow(() -> new RuntimeException("ultimo rol no encontrado"));
            // Genera un token JWT con el nombre de usuario y rol
            return jwtUtil.generarToken(nombreUsuario, presentaEntidad.getRol().getNombre());
        } else {
            throw new RuntimeException("credenciales invalidas");
        }
    }
}
