package com.example.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component // Marca la clase como un componente gestionado por Spring
public class JwtUtil {

    // Clave secreta generada automáticamente para firmar los tokens JWT
    private final Key llaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Genera un token JWT para un usuario específico con un rol asociado.
     *
     * @param usuario El nombre del usuario.
     * @param rol El rol asignado al usuario.
     * @return El token JWT generado.
     */
    public String generarToken(String usuario, String rol) {
        return Jwts.builder()
                .setSubject(usuario) // Establece el usuario como el sujeto del token
                .claim("rol", rol) // Agrega un atributo de rol al token
                .setIssuedAt(new Date()) // Establece la fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Define una expiración (1 hora)
                .signWith(llaveSecreta, SignatureAlgorithm.HS256) // Firma el token con la clave secreta
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token El token JWT del cual extraer el usuario.
     * @return El nombre del usuario contenido en el token.
     */
    
    public String estraerUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(llaveSecreta) // Configura la clave secreta para desencriptar
                .build()
                .parseClaimsJws(token) // Parse el token JWT
                .getBody()
                .getSubject(); // Obtiene el sujeto (nombre de usuario)
    }

    /**
     * Valida si un token JWT es válido y no ha expirado.
     *
     * @param token El token JWT a validar.
     * @return `true` si el token es válido; de lo contrario, `false`.
     */
    public boolean validarToken(String token) {
        try {
            // Intenta desencriptar el token para validar su firma y estructura
            Jwts.parserBuilder()
                .setSigningKey(llaveSecreta)
                .build()
                .parseClaimsJws(token);
            return true; // El token es válido
        } catch (Exception e) {
            return false; // El token es inválido
        }
    }

    /**
     * Devuelve la clave secreta utilizada para firmar y validar los tokens JWT.
     *
     * @return La clave secreta.
     */
    public Key getLLaveSecreta() {
        return llaveSecreta;
    }
}
