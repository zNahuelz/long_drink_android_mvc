package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private int idusuario;
    private String contrasena;
    @Column(name="nombre_usuario")
    private String nombreusuario;
    private int permisos;
    private int activo;

    public Usuario(){}

    public Usuario(int id_usuario, String contrasena, String nombre_usuario, int permisos, int activo) {
        this.idusuario = id_usuario;
        this.contrasena = contrasena;
        this.nombreusuario = nombre_usuario;
        this.permisos = permisos;
        this.activo = activo;
    }

    public boolean isEmpty() {
        return this.getContrasena() == null && this.getNombre_usuario() == null && this.getPermisos() == 1
                && this.getActivo() != 0 || this.getActivo() != 1;
    }

    public int getId_usuario() {
        return idusuario;
    }

    public void setId_usuario(int id_usuario) {
        this.idusuario = id_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre_usuario() {
        return nombreusuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombreusuario = nombre_usuario;
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
