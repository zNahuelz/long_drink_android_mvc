package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name="alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_alumno;
    private String ap_materno;
    private String ap_paterno;
    private String dni;
    private String email;
    private String nombre;
    private int activo;

    public Alumno(){}

    public Alumno(int id_alumno, String ap_materno, String ap_paterno, String dni, String email, String nombre, int activo) {
        this.id_alumno = id_alumno;
        this.ap_materno = ap_materno;
        this.ap_paterno = ap_paterno;
        this.dni = dni;
        this.email = email;
        this.nombre = nombre;
        this.activo = activo;
    }

    public boolean isEmpty(){
        return this.getNombre() == null && this.getAp_paterno() == null
                && this.getAp_materno() == null && this.getDni() == null && this.getEmail() == null &&
                this.getActivo() != 0 || this.getActivo() != 1;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
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
