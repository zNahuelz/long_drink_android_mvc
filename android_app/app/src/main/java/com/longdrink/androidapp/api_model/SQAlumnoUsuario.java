package com.longdrink.androidapp.api_model;

public class SQAlumnoUsuario {

    private int id_alumno;
    private int id_usuario;

    public SQAlumnoUsuario(int id_alumno, int id_usuario) {
        this.id_alumno = id_alumno;
        this.id_usuario = id_usuario;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
