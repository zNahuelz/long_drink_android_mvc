package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_profesor;
    private String ap_materno;
    private String ap_paterno;
    private String dni;
    private String email;
    private String foto;
    private String nombre;
    private int activo;

    public Profesor(){}

    public Profesor(int id_profesor, String ap_materno, String ap_paterno, String dni, String email, String foto, String nombre, int activo) {
        this.id_profesor = id_profesor;
        this.ap_materno = ap_materno;
        this.ap_paterno = ap_paterno;
        this.dni = dni;
        this.email = email;
        this.foto = foto;
        this.nombre = nombre;
        this.activo = activo;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
