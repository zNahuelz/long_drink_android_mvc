package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso_tema")
public class CursoTema {
    @Id
    private int id_tema;
    @Id
    private int id_curso;

    public CursoTema(){}

    public CursoTema(int id_tema, int id_curso) {
        this.id_tema = id_tema;
        this.id_curso = id_curso;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
        this.id_tema = id_tema;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }
}
