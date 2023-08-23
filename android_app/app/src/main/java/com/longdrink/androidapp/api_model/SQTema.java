package com.longdrink.androidapp.api_model;

import java.io.Serializable;

public class SQTema implements Serializable {
    private int id_tema;
    private String nombre;
    private String guia;
    private int activos;

    public SQTema(int id_tema, String nombre, String guia, int activos) {
        this.id_tema = id_tema;
        this.nombre = nombre;
        this.guia = guia;
        this.activos = activos;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
        this.id_tema = id_tema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public int getActivos() {
        return activos;
    }

    public void setActivos(int activos) {
        this.activos = activos;
    }
}
