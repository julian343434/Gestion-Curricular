package com.example.demo.modelo.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.entidades.UsuarioEntidad;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, Long> {
}
