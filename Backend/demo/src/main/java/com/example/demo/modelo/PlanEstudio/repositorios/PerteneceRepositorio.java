package com.example.demo.modelo.PlanEstudio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.PlanEstudio.entidades.PerteneceEntidad;
import com.example.demo.modelo.PlanEstudio.entidades.PerteneceIdEntidad;

public interface PerteneceRepositorio extends JpaRepository<PerteneceEntidad, PerteneceIdEntidad>{

}
