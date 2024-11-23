package com.example.demo.modelo.usuarios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modelo.usuarios.entidades.UsuarioEntidad;
import java.util.Optional;

@Repository // Marca esta interfaz como un componente de repositorio Spring
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, Long> {

    // Busca un usuario por correo electrónico
    Optional<UsuarioEntidad> findByCorreo(String correo);

    // Busca un usuario por su nombre de usuario único
    Optional<UsuarioEntidad> findByNombreUsuario(String nombre_usuario);
}
