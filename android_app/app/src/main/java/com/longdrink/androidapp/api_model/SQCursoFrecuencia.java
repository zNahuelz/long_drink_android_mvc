package com.longdrink.androidapp.api_model;

public class SQCursoFrecuencia {
    private int id_curso;
    private int id_frecuencia;

    public SQCursoFrecuencia(){}

    public SQCursoFrecuencia(int id_curso, int id_frecuencia) {
        this.id_curso = id_curso;
        this.id_frecuencia = id_frecuencia;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public int getId_frecuencia() {
        return id_frecuencia;
    }

    public void setId_frecuencia(int id_frecuencia) {
        this.id_frecuencia = id_frecuencia;
    }
}
