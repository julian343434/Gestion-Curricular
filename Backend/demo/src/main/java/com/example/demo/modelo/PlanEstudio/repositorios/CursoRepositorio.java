package com.example.demo.modelo.PlanEstudio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.PlanEstudio.entidades.CursoEntidad;

import jakarta.transaction.Transactional;

@Repository
public interface CursoRepositorio extends JpaRepository<CursoEntidad, Long>{
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM curso WHERE id_curso IN (SELECT id_curso FROM pertenece WHERE id_plan_estudio = :idPlanEstudio)", nativeQuery = true)
    void deleteCursosByPlanEstudio(@Param("idPlanEstudio") Long idPlanEstudio);
}
