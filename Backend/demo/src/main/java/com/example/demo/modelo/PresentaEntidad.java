package com.example.demo.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;

@Entity
public class PresentaEntidad {

    @EmbeddedId
    private PresentaIdEntidad id;

    @ManyToOne
    @MapsId("id_usuario")
    @JoinColumn(name = "id_usuario")
    private UsuarioEntidad usuario;

    

}
