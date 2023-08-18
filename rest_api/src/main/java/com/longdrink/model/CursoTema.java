package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_tema")
@IdClass(CursoTema.class)
public class CursoTema {
    @Id
    @Column(name="id_tema")
    private int idTema;
    @Id
    @Column(name="id_curso")
    private int idCurso;

    public CursoTema(){}

    public CursoTema(int id_tema, int id_curso) {
        this.idTema = id_tema;
        this.idCurso = id_curso;
    }

    public int getId_tema() {
        return idTema;
    }

    public void setId_tema(int id_tema) {
        this.idTema = id_tema;
    }

    public int getId_curso() {
        return idCurso;
    }

    public void setId_curso(int id_curso) {
        this.idCurso = id_curso;
    }
}
