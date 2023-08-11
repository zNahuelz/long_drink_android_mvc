package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_alumno")
@IdClass(UsuarioAlumno.class)
public class UsuarioAlumno {
    @Id
    @Column(name="id_usuario")
    private int idUsuario;
    @Id
    @Column(name="id_alumno")
    private int idAlumno;

    public UsuarioAlumno(){}

    public UsuarioAlumno(int id_usuario, int id_alumno) {
        this.idUsuario = id_usuario;
        this.idAlumno = id_alumno;
    }

    public int getId_usuario() {
        return idUsuario;
    }

    public void setId_usuario(int id_usuario) {
        this.idUsuario = id_usuario;
    }

    public int getId_alumno() {
        return idAlumno;
    }

    public void setId_alumno(int id_alumno) {
        this.idAlumno = id_alumno;
    }
}
