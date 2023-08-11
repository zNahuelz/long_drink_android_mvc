package com.longdrink.androidapp.api_model;

import java.io.Serializable;

public class SQFrecuencia implements Serializable {
    private int id_frecuencia;
    private String nombre;
    private int activo;

    public SQFrecuencia(){}

    public SQFrecuencia(int id_frecuencia, String nombre, int activo) {
        this.id_frecuencia = id_frecuencia;
        this.nombre = nombre;
        this.activo = activo;
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
