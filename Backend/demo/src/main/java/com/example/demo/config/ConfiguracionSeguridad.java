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
import org.springframework.http.HttpMethod; // Importación agregada

import com.example.demo.util.JwtAuthenticationFilter;

import java.util.Arrays;

@Configuration
public class ConfiguracionSeguridad {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public ConfiguracionSeguridad(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuario/**").hasRole("Administrador") // Requiere rol ADMIN
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permitir OPTIONS
                .anyRequest().permitAll() // Todas las demás no requieren autenticación
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agrega el filtro JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Aquí puedes agregar más orígenes si es necesario
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://otro-origen.com")); // Orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS" ,"PATCH")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permitir todos los encabezados
        configuration.setAllowCredentials(true); // Permitir credenciales (cookies, headers)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar configuración a todas las rutas
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
