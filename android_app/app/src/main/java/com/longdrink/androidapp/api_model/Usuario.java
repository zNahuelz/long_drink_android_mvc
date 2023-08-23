package com.longdrink.androidapp.api_model;

public class Usuario {
    private int id_usuario;
    private String contrasena;
    private String nombre_usuario;
    private int permisos;
    private int activo;

    public Usuario(){}

    public Usuario(int id_usuario, String contrasena, String nombre_usuario, int permisos, int activo) {
        this.id_usuario = id_usuario;
        this.contrasena = contrasena;
        this.nombre_usuario = nombre_usuario;
        this.permisos = permisos;
        this.activo = activo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int idusuario) {
        this.id_usuario = idusuario;
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

    public void setNombre_usuario(String nombreusuario) {
        this.nombre_usuario = nombreusuario;
    }

    public int getPermisos() {
        return permisos;
    }

    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
