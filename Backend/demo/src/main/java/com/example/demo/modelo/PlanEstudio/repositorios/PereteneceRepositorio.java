package com.example.demo.modelo.PlanEstudio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.usuarios.entidades.PresentaEntidad;
import com.example.demo.modelo.usuarios.entidades.PresentaIdEntidad;

public interface PereteneceRepositorio extends JpaRepository<PresentaEntidad, PresentaIdEntidad>{

}
