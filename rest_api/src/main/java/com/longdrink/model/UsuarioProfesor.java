package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_profesor")
@IdClass(UsuarioProfesor.class)
public class UsuarioProfesor {
    @Id
    @Column(name="id_usuario")
    private int idUsuario;
    @Id
    @Column(name="id_profesor")
    private int idProfesor;

    public UsuarioProfesor(){}

    public UsuarioProfesor(int id_usuario, int id_profesor) {
        this.idUsuario = id_usuario;
        this.idProfesor = id_profesor;
    }

    public int getId_usuario() {
        return idUsuario;
    }

    public void setId_usuario(int id_usuario) {
        this.idUsuario = id_usuario;
    }

    public int getId_profesor() {
        return idProfesor;
    }

    public void setId_profesor(int id_profesor) {
        this.idProfesor = id_profesor;
    }
}
