package com.example.demo.controlador.usuarios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.modelo.usuarios.entidades.PresentaEntidad;
import com.example.demo.modelo.usuarios.entidades.RolEntidad;
import com.example.demo.modelo.usuarios.entidades.UsuarioEntidad;
import com.example.demo.modelo.usuarios.servicios.PresentaServicio;
import com.example.demo.modelo.usuarios.servicios.RolServicio;
import com.example.demo.modelo.usuarios.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // Permite solicitudes desde un origen específico
@RestController // Define la clase como controlador REST
@RequestMapping("/usuario") // Prefijo de ruta para este controlador
public class Usuario {

    @Autowired
    private UsuarioServicio usuarioServicio; // Servicio para manejar usuarios
    @Autowired
    private PresentaServicio presentaServicio; // Servicio para relaciones usuario-rol
    @Autowired
    private RolServicio rolServicio; // Servicio para roles

    /**
     * Obtiene una lista de todos los usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('Administrador')") 
    public List<UsuarioEntidad> obetenerUsuarios() {
        return usuarioServicio.obtenerUsuarios();
    }

    /**
     * Guarda un nuevo usuario junto con su rol y periodo.
     *
     * @param datos Mapa que contiene la información del usuario, rol y periodo.
     * @return El usuario creado.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Administrador')") // Restringe el acceso solo a usuarios con rol de Administrador
    public UsuarioEntidad guardarUsuario(@RequestBody Map<String, Object> datos) {

        RolEntidad rol = rolServicio.obtenerRol((String) datos.get("rol")); // Obtiene el rol por su nombre
        datos.remove("rol");

        UsuarioEntidad nuevoUsuario = usuarioServicio.guardarUsuario((Map<String, Object>) datos.get("usuario")); // Crea el usuario
        datos.remove("usuario");

        // Crea y guarda la relación usuario-rol
        PresentaEntidad nuevaRelacion = presentaServicio.crearRelacion(nuevoUsuario.getId(), rol.getId(), datos);
        nuevaRelacion.setUsuario(nuevoUsuario); 
        nuevaRelacion.setRol(rol);

        presentaServicio.guardarRelacion(nuevaRelacion);

        nuevoUsuario.getRol().add(nuevaRelacion); // Agrega la relación al usuario

        return nuevoUsuario;
    }

    /**
     * Actualiza un usuario existente y opcionalmente su rol.
     *
     * @param id ID del usuario a actualizar.
     * @param campos Campos a modificar, incluyendo rol, periodo y año si aplica.
     * @return El usuario actualizado.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')") // Restringe el acceso solo a usuarios con rol de Administrador
    public UsuarioEntidad actulizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> campos){

        UsuarioEntidad usuario = usuarioServicio.obtenerUsuario(id);

        if(campos.containsKey("rol")){
            RolEntidad rol = rolServicio.obtenerRol((String) campos.get("rol"));
            Map<String, Object> datos = new HashMap<>();
            if(!campos.containsKey("anio") || !campos.containsKey("periodo")){
                throw new RuntimeException("Informacion insuficiente para cambiar rol falta anio o periodo");
            }
            datos.put("periodo", campos.get("periodo"));
            datos.put("anio", campos.get("anio"));
            PresentaEntidad nuevaRelacion = presentaServicio.crearRelacion(usuario.getId(), rol.getId(), datos);

            campos.remove("periodo");
            campos.remove("anio");
            campos.remove("rol");

            nuevaRelacion.setUsuario(usuario);
            nuevaRelacion.setRol(rol);

            usuario.getRol().add(nuevaRelacion);
            presentaServicio.guardarRelacion(nuevaRelacion);
        }

        if(!campos.isEmpty()){
            usuarioServicio.actulizarUsuario(usuario, campos);
        }
        
        return usuario;
    }

    /**
     * Desactiva un usuario, marcándolo como inactivo.
     *
     * @param id ID del usuario a desactivar.
     * @return El usuario desactivado.
     */
    @PatchMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Administrador')") // Restringe el acceso solo a usuarios con rol de Administrador
    public UsuarioEntidad desactivarUsuario(@PathVariable Long id){
        UsuarioEntidad usuario = usuarioServicio.obtenerUsuario(id);
        usuarioServicio.desactivarUsuario(usuario);
        return usuario;
    }

    /**
     * Activa un usuario previamente desactivado.
     *
     * @param id ID del usuario a activar.
     * @return El usuario activado.
     */
    @PatchMapping("/activar/{id}")
    @PreAuthorize("hasRole('Administrador')") // Restringe el acceso solo a usuarios con rol de Administrador
    public UsuarioEntidad activarUsuario(@PathVariable Long id){
        UsuarioEntidad usuario = usuarioServicio.obtenerUsuario(id);
        usuarioServicio.activarUsuario(usuario);
        return usuario;
    }

}
