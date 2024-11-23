package com.example.demo.modelo.usuarios.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modelo.usuarios.entidades.RolEntidad;

@Repository // Marca la interfaz como repositorio de Spring
public interface RolRepositorio extends JpaRepository<RolEntidad, Long> {
   
   // Encuentra un rol por su nombre
   Optional<RolEntidad> findByNombre(String nombre);
}
