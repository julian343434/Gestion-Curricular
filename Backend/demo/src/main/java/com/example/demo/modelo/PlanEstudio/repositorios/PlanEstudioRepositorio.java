package com.example.demo.modelo.PlanEstudio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.PlanEstudio.entidades.PlanEstudioEntidad;

public interface PlanEstudioRepositorio extends JpaRepository<PlanEstudioEntidad, Long>{

}
