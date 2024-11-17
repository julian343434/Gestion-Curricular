package com.example.demo.modelo.repositorios;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.entidades.PresentaEntidad;
import com.example.demo.modelo.entidades.PresentaIdEntidad;

@Repository
public interface PresentaRepositorio extends JpaRepository<PresentaEntidad, PresentaIdEntidad> {

    // Encuentra el último rol para un usuario, basado en anio y periodo
    Optional<PresentaEntidad> findFirstByUsuarioIdOrderByIdAnioDescIdPeriodoDesc(Long usuarioId);

}
