package com.longdrink.androidapp.api_model;

import java.util.Date;

public class SQProfesorCurso {
    private int idprofesor;
    private int idcurso;
    private Date fecha_inicio_curso;
    private Date fecha_final_curso;

    public SQProfesorCurso(){}

    public SQProfesorCurso(int id_profesor, int id_curso, Date fecha_inicio_curso, Date fecha_final_curso) {
        this.idprofesor = id_profesor;
        this.idcurso = id_curso;
        this.fecha_inicio_curso = fecha_inicio_curso;
        this.fecha_final_curso = fecha_final_curso;
    }

    public int getId_profesor() {
        return idprofesor;
    }

    public void setId_profesor(int id_profesor) {
        this.idprofesor = id_profesor;
    }

    public int getId_curso() {
        return idcurso;
    }

    public void setId_curso(int id_curso) {
        this.idcurso = id_curso;
    }

    public Date getFecha_inicio_curso() {
        return fecha_inicio_curso;
    }

    public void setFecha_inicio_curso(Date fecha_inicio_curso) {
        this.fecha_inicio_curso = fecha_inicio_curso;
    }

    public Date getFecha_final_curso() {
        return fecha_final_curso;
    }

    public void setFecha_final_curso(Date fecha_final_curso) {
        this.fecha_final_curso = fecha_final_curso;
    }
}
