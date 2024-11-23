package com.example.demo.modelo.PlanEstudio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.PlanEstudio.entidades.CursoEntidad;

@Repository
public interface CursoRepositorio extends JpaRepository<CursoEntidad, Long>{

}
