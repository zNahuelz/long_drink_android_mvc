package com.longdrink.androidapp.api_model;

public class SQRegistro {
    private String nombre;
    private String appaterno;
    private String apmaterno;
    private String dni;
    private String email;
    private String contrasena;
    private int permisos;

    public SQRegistro(){}

    public SQRegistro(String nombre, String appaterno, String apmaterno, String dni, String email, String contrasena, int permisos) {
        this.nombre = nombre;
        this.appaterno = appaterno;
        this.apmaterno = apmaterno;
        this.dni = dni;
        this.email = email;
        this.contrasena = contrasena;
        this.permisos = permisos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAppaterno() {
        return appaterno;
    }

    public void setAppaterno(String appaterno) {
        this.appaterno = appaterno;
    }

    public String getApmaterno() {
        return apmaterno;
    }

    public void setApmaterno(String apmaterno) {
        this.apmaterno = apmaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getPermisos() {
        return permisos;
    }

    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }
    public String generarUsername(){
        return this.nombre.trim().toUpperCase().substring(0,1) + this.appaterno.trim().toUpperCase().substring(0,1) + this.apmaterno.trim().toUpperCase().substring(0,1)+this.dni;
    }
}
