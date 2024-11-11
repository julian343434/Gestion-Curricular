package com.example.demo.modelo.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.entidades.RolEntidad;

@Repository
public interface RolRepositorio extends JpaRepository<RolEntidad, Long> {
}
