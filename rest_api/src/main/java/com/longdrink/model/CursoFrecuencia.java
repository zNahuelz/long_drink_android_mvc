package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_frecuencia")
@IdClass(CursoFrecuencia.class)
public class CursoFrecuencia {
    @Id
    @Column(name="id_curso")
    private int idCurso;
    @Id
    @Column(name="id_frecuencia")
    private int idFrecuencia;

    public CursoFrecuencia(){}

    public CursoFrecuencia(int id_curso, int id_frecuencia) {
        this.idCurso = id_curso;
        this.idFrecuencia = id_frecuencia;
    }

    public int getId_curso() {
        return idCurso;
    }

    public void setId_curso(int id_curso) {
        this.idCurso = id_curso;
    }

    public int getId_frecuencia() {
        return idFrecuencia;
    }

    public void setId_frecuencia(int id_frecuencia) {
        this.idFrecuencia = id_frecuencia;
    }
}
