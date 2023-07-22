package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "frecuencia")
public class Frecuencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_frecuencia;
    private String nombre;
    private int activo;

    public Frecuencia(){}

    public Frecuencia(int id_frecuencia, String nombre, int activo) {
        this.id_frecuencia = id_frecuencia;
        this.nombre = nombre;
        this.activo = activo;
    }

    public boolean isEmpty(){
        return this.nombre == null && this.getActivo() >=2 || this.getActivo() <0;
    }

    public int getId_frecuencia() {
        return id_frecuencia;
    }

    public void setId_frecuencia(int id_frecuencia) {
        this.id_frecuencia = id_frecuencia;
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
