package com.longdrink.androidapp.api_model;

public class SQUsuario {
    private String contrasena;
    private String nombre_usuario;

    public SQUsuario(){}

    public SQUsuario( String nombre_usuario, String contrasena) {
        this.contrasena = contrasena;
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
}
