package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import com.example.demo.util.JwtAuthenticationFilter;

import java.util.Arrays;

@Configuration // Define esta clase como una configuración de Spring
public class ConfiguracionSeguridad {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor que inyecta el filtro de autenticación JWT
    public ConfiguracionSeguridad(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura la cadena de filtros de seguridad para las solicitudes HTTP.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad.
     * @return Una instancia de SecurityFilterChain.
     * @throws Exception Si ocurre un error al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Desactiva CSRF para simplificar solicitudes desde frontend
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS con configuración personalizada
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuario/**").hasRole("Administrador") // Protege rutas de usuario con el rol ADMIN
                .requestMatchers("/planEstudio/**").hasRole("Administrador") 
                 // Endpoints públicos
                 .requestMatchers(
                    "/planEstudio/",
                    "/planEstudio/{id}",
                    "/planEstudio/planEstudio/{id}",
                    "/planEstudio/curso/{id}",
                    "/planEstudio/{id}/archivo/descargar"
                ).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite solicitudes OPTIONS (preflight CORS)
                .anyRequest().permitAll() // Permite todas las demás solicitudes sin autenticación
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agrega el filtro JWT antes del filtro de autenticación predeterminado

        return http.build();
    }

    /**
     * Configuración de CORS para permitir solicitudes desde dominios específicos.
     *
     * @return Una configuración de fuente CORS personalizada.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://otro-origen.com")); // Orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite credenciales como cookies y headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración a todas las rutas
        return source;
    }

    /**
     * Define el bean para el codificador de contraseñas, usando BCrypt.
     *
     * @return Un codificador de contraseñas BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define el bean para el administrador de autenticación.
     *
     * @param configuration Configuración de autenticación de Spring Security.
     * @return Una instancia de AuthenticationManager.
     * @throws Exception Si ocurre un error al obtener el administrador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
