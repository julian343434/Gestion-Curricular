package com.example.demo.modelo.usuarios.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modelo.usuarios.entidades.PresentaEntidad;
import com.example.demo.modelo.usuarios.entidades.PresentaIdEntidad;

@Repository // Indica que esta interfaz es un repositorio Spring para manejo de datos
public interface PresentaRepositorio extends JpaRepository<PresentaEntidad, PresentaIdEntidad> {

    // Busca el último registro de un usuario según año y periodo en orden descendente
    Optional<PresentaEntidad> findFirstByUsuarioIdOrderByIdAnioDescIdPeriodoDesc(Long usuarioId);
}
