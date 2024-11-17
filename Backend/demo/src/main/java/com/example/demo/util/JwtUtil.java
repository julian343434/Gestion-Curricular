package com.example.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key llaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generarToken(String usuario, String rol){

        return Jwts.builder()
                .setSubject(usuario)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(llaveSecreta, SignatureAlgorithm.HS256)
                .compact();
    }

    public String estraerUsuario(String token) {

        return Jwts.parserBuilder() // Usa parserBuilder en lugar de parser
                .setSigningKey(llaveSecreta) // Configura la clave secreta como objeto Key
                .build() // Construye el parser
                .parseClaimsJws(token) // Parse el token
                .getBody()
                .getSubject(); // Extrae el "subject" (en este caso, el username)
    }

    public boolean validarToken(String token) {

        try {
            Jwts.parserBuilder()
                .setSigningKey(llaveSecreta) // Configura la clave secreta como Key
                .build()
                .parseClaimsJws(token); // Valida el token
            return true; // Token válido
        } catch (Exception e) {
            return false; // Token inválido
        }
    }

    public Key getLLaveSecreta() { return llaveSecreta; }
    
}
