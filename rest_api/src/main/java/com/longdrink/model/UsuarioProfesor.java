package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_profesor")
public class UsuarioProfesor {
    @Id
    private int id_usuario;
    @Id
    private int id_profesor;

    public UsuarioProfesor(){}

    public UsuarioProfesor(int id_usuario, int id_profesor) {
        this.id_usuario = id_usuario;
        this.id_profesor = id_profesor;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }
}
