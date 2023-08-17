package com.longdrink.androidapp.api_model;

public class FrontFrecuenciaTurno {
    private int id_frecuencia;
    private int id_turno;

    public FrontFrecuenciaTurno(){}

    public FrontFrecuenciaTurno(int id_frecuencia, int id_turno) {
        this.id_frecuencia = id_frecuencia;
        this.id_turno = id_turno;
    }

    public int getId_frecuencia() {
        return id_frecuencia;
    }

    public void setId_frecuencia(int id_frecuencia) {
        this.id_frecuencia = id_frecuencia;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }
}
