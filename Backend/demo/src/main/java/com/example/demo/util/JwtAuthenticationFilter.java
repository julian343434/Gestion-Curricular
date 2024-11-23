package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Component // Marca la clase como un componente gestionado por Spring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // Utilidad para manejar operaciones JWT

      /**
     * Método principal que intercepta y valida las solicitudes entrantes.
     *
     * @param request El objeto HttpServletRequest de la solicitud.
     * @param response El objeto HttpServletResponse de la respuesta.
     * @param filterChain La cadena de filtros donde pasa la solicitud si es válida.
     * @throws ServletException Si ocurre un error relacionado con el filtro.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // Obtiene el encabezado "Authorization" de la solicitud
        String header = request.getHeader("Authorization");

        // Comprueba si el encabezado tiene el formato esperado y contiene un token
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Extrae el token después de "Bearer "

            // Valida el token
            if (jwtUtil.validarToken(token)) {
                // Extrae el nombre de usuario del token
                String username = jwtUtil.estraerUsuario(token);

                // Extrae el rol del token usando la misma clave para desencriptar
                String rol = Jwts.parserBuilder()
                        .setSigningKey(jwtUtil.getLLaveSecreta())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .get("rol", String.class); // Extrae el rol

                // Crea un objeto de autenticación para el contexto de seguridad
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, // Usuario autenticado
                        null, // No se requiere contraseña en este punto
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol)) // Asigna roles
                );

                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continúa la cadena de filtros independientemente de la validación
        filterChain.doFilter(request, response);
    }

}
