package com.longdrink.androidapp.api_model;

public class SQCursoTema {
    private int id_curso;
    private int id_tema;

    public SQCursoTema(int id_curso, int id_tema) {
        this.id_curso = id_curso;
        this.id_tema = id_tema;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
        this.id_tema = id_tema;
    }
}
