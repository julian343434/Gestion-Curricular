package com.example.demo.modelo.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.entidades.PresentaEntidad;
import com.example.demo.modelo.entidades.PresentaIdEntidad;

@Repository
public interface PresentaRepositorio extends JpaRepository<PresentaEntidad, PresentaIdEntidad> {
}
