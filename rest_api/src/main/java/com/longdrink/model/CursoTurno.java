package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso_turno")
public class CursoTurno {
    @Id
    private int id_curso;
    @Id
    private int id_turno;

    public CursoTurno(){}

    public CursoTurno(int id_curso, int id_turno) {
        this.id_curso = id_curso;
        this.id_turno = id_turno;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }
}
