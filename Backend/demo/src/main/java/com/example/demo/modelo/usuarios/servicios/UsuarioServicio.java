package com.example.demo.modelo.usuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import com.example.demo.modelo.usuarios.entidades.UsuarioEntidad;
import com.example.demo.modelo.usuarios.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.util.ArrayList;

@Service // Define esta clase como un servicio gestionado por Spring
public class UsuarioServicio {
     
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;
 
    // Obtiene todos los usuarios registrados
    public List<UsuarioEntidad> obtenerUsuarios(){
        List<UsuarioEntidad> usuarios = usuarioRepositorio.findAll();
        if(usuarios.isEmpty()){
            throw new RuntimeException("Sin usuarios registrados");
        }
        return usuarios;
    }

    // Obtiene un usuario por su ID
    public UsuarioEntidad obtenerUsuario(Long id){
        return usuarioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("usario " + id + " no encontrado"));
    }

    // Crea y guarda un nuevo usuario
    public UsuarioEntidad guardarUsuario(Map<String, Object> usuario){
        nombreUsuarioDuplicado((String) usuario.get("nombre_usuario"));
        correoDuplicado((String) usuario.get("correo"));

        UsuarioEntidad nuevoUsuario = new UsuarioEntidad();
        nuevoUsuario.setNombre((String) usuario.get("nombre"));
        nuevoUsuario.setNombreUsuario((String) usuario.get("nombre_usuario"));
        nuevoUsuario.setCorreo((String) usuario.get("correo"));
        nuevoUsuario.setActivo((boolean) usuario.get("activo"));
        nuevoUsuario.setContrasena(passwordEncoder.encode((String) usuario.get("contrasena")));
        nuevoUsuario.setRol(new ArrayList<>());
        return usuarioRepositorio.save(nuevoUsuario);
    }

    // Valida si el correo ya está registrado
    public void correoDuplicado(String correo){
        if(usuarioRepositorio.findByCorreo(correo).isPresent()){
            throw new RuntimeException("Correo ya esta en uso");
        }
    }

    // Valida si el nombre de usuario ya está registrado
    public void nombreUsuarioDuplicado(String nombre){
        if(usuarioRepositorio.findByNombreUsuario(nombre).isPresent()){
            throw new RuntimeException("Nombre de usuario ya esta en uso");
        }
    }

    // Actualiza los datos de un usuario
    public void actulizarUsuario(UsuarioEntidad usuario, Map<String, Object> campos){
        campos.forEach((key, value) -> {
            if(key == "correo"){
                correoDuplicado((String) value);
            }
            if (key == "nombre_usuario") {
                nombreUsuarioDuplicado((String) value);
                key = "nombreUsuario";
            }
            Field field = ReflectionUtils.findField(UsuarioEntidad.class, key);
            if(field != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, usuario, value);
            }else{
                throw new RuntimeException("Campo no encontrado " + key);
            }
        });
        usuarioRepositorio.save(usuario);
    }

    // Desactiva un usuario
    public void desactivarUsuario(UsuarioEntidad usuario){
        usuario.setActivo(false);
        usuarioRepositorio.save(usuario);
    }

    // Activa un usuario
    public void activarUsuario(UsuarioEntidad usuario){
        usuario.setActivo(true);
        usuarioRepositorio.save(usuario);
    }
}
