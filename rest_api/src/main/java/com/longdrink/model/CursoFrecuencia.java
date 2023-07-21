package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso_frecuencia")
public class CursoFrecuencia {
    @Id
    private int id_curso;
    @Id
    private int id_frecuencia;

    public CursoFrecuencia(){}

    public CursoFrecuencia(int id_curso, int id_frecuencia) {
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
