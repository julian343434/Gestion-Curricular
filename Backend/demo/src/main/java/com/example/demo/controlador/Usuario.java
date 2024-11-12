package com.example.demo.controlador;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.entidades.PresentaEntidad;
import com.example.demo.modelo.entidades.RolEntidad;
import com.example.demo.modelo.entidades.UsuarioEntidad;
import com.example.demo.modelo.repositorios.RolRepositorio;
import com.example.demo.modelo.servicios.PresentaServicio;
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
    private RolRepositorio rolRepositorio;

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
    public UsuarioEntidad guardarUsuario(@RequestBody Map<String, Object> datos) {

        UsuarioEntidad nuevoUsuario = usuarioServicio.guardarUsuario((Map<String, Object>) datos.get("usuario"));
        datos.remove("usuario");
        RolEntidad rol = rolRepositorio.findByNombre((String) datos.get("rol"));
        datos.remove("rol");
        PresentaEntidad nuevaRelacion = presentaServicio.crearRelacion(nuevoUsuario.getId(), rol.getId(), datos);
        

        nuevaRelacion.setUsuario(nuevoUsuario);
        nuevaRelacion.setRol(rol);

        presentaServicio.guardarRelacion(nuevaRelacion);

        nuevoUsuario.getRol().add(nuevaRelacion);

        return nuevoUsuario;
    }
    
}
