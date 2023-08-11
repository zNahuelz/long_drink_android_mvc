package com.longdrink.androidapp.api_model;

import java.io.Serializable;
import java.util.Date;

public class SQTurno implements Serializable {
    private int id_turno;
    private String nombre;
    private Date hora_inicio;
    private Date hora_final;

    public SQTurno(){}

    public SQTurno(int id_turno, String nombre, Date hora_inicio, Date hora_final) {
        this.id_turno = id_turno;
        this.nombre = nombre;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Date hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Date getHora_final() {
        return hora_final;
    }

    public void setHora_final(Date hora_final) {
        this.hora_final = hora_final;
    }
}
