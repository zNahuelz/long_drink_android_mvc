package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_turno")
@IdClass(CursoTurno.class)
public class CursoTurno {
    @Id
    @Column(name="id_curso")
    private int idCurso;
    @Id
    @Column(name="id_turno")
    private int idTurno;

    public CursoTurno(){}

    public CursoTurno(int id_curso, int id_turno) {
        this.idCurso = id_curso;
        this.idTurno = id_turno;
    }

    public int getId_curso() {
        return idCurso;
    }

    public void setId_curso(int id_curso) {
        this.idCurso = id_curso;
    }

    public int getId_turno() {
        return idTurno;
    }

    public void setId_turno(int id_turno) {
        this.idTurno = id_turno;
    }
}
