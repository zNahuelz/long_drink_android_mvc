package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tema")
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tema;
    private String nombre;
    private String guia;
    private int activo;

    public Tema(){}

    public Tema(int id_tema, String nombre, String guia, int activo) {
        this.id_tema = id_tema;
        this.nombre = nombre;
        this.guia = guia;
        this.activo = activo;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
