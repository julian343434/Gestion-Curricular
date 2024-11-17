package com.example.demo.controlador;
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

import com.example.demo.modelo.entidades.PresentaEntidad;
import com.example.demo.modelo.entidades.RolEntidad;
import com.example.demo.modelo.entidades.UsuarioEntidad;
import com.example.demo.modelo.servicios.PresentaServicio;
import com.example.demo.modelo.servicios.RolServicio;
import com.example.demo.modelo.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
public class Usuario {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PresentaServicio presentaServicio;
    @Autowired
    private RolServicio rolServicio;

    @GetMapping("/")
    public List<UsuarioEntidad> obetenerUsuarios() {
        return usuarioServicio.obtenerUsuarios();
    }

    /* como se espera que se envien los datos
    {
        "usuario": {
            "nombre": "Juan",
            "nombre_usuario": "jdoe",
            "contrasena": "secreta",
            "correo": "juan@correo.com",
            "activo": true
        },
        "rol": Estudiante,
        "periodo": 1,
        "anio": 2023
    }
    */

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Administrador')")
    public UsuarioEntidad guardarUsuario(@RequestBody Map<String, Object> datos) {

        RolEntidad rol = rolServicio.obtenerRol((String) datos.get("rol"));
        datos.remove("rol");

        UsuarioEntidad nuevoUsuario = usuarioServicio.guardarUsuario((Map<String, Object>) datos.get("usuario"));
        datos.remove("usuario");

        PresentaEntidad nuevaRelacion = presentaServicio.crearRelacion(nuevoUsuario.getId(), rol.getId(), datos);
        
        nuevaRelacion.setUsuario(nuevoUsuario);
        nuevaRelacion.setRol(rol);

        presentaServicio.guardarRelacion(nuevaRelacion);

        nuevoUsuario.getRol().add(nuevaRelacion);

        return nuevoUsuario;
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public UsuarioEntidad actuliazrUsuario(@PathVariable Long id, @RequestBody Map<String, Object> campos){

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

    @PatchMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public UsuarioEntidad desactivarUsuario(@PathVariable Long id){
        UsuarioEntidad usuario = usuarioServicio.obtenerUsuario(id);
        usuarioServicio.desactivarUsuario(usuario);
        return usuario;
    }

    @PatchMapping("/activar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public UsuarioEntidad activarUsuario(@PathVariable Long id){
        UsuarioEntidad usuario = usuarioServicio.obtenerUsuario(id);
        usuarioServicio.activarUsuario(usuario);
        return usuario;
    }



}
