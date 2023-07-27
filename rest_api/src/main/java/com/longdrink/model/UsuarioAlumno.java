package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_alumno")
@IdClass(UsuarioAlumno.class)
public class UsuarioAlumno {
    @Id
    private int id_usuario;
    @Id
    private int id_alumno;

    public UsuarioAlumno(){}

    public UsuarioAlumno(int id_usuario, int id_alumno) {
        this.id_usuario = id_usuario;
        this.id_alumno = id_alumno;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }
}
