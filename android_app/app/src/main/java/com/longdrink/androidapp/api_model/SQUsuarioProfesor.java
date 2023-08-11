package com.longdrink.androidapp.api_model;

public class SQUsuarioProfesor {
    private int id_profesor;
    private int id_usuario;

    public SQUsuarioProfesor(){}

    public SQUsuarioProfesor(int id_profesor, int id_usuario) {
        this.id_profesor = id_profesor;
        this.id_usuario = id_usuario;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
