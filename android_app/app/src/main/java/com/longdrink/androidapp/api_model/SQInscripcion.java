package com.longdrink.androidapp.api_model;

import java.util.Date;

public class SQInscripcion {
    private int id_alumno;
    private int id_curso;
    private String fechainiciocurso;
    private String fechafinalcurso;
    private String fechainscripcion;
    private int terminado;
    private int activo;

    public SQInscripcion(){}

    public SQInscripcion(int idalumno, int idcurso, String fechainiciocurso, String fechafinalcurso, String fechainscripcion, int terminado, int activo) {
        this.id_alumno = idalumno;
        this.id_curso = idcurso;
        this.fechainiciocurso = fechainiciocurso;
        this.fechafinalcurso = fechafinalcurso;
        this.fechainscripcion = fechainscripcion;
        this.terminado = terminado;
        this.activo = activo;
    }

    public int getIdalumno() {
        return id_alumno;
    }

    public void setIdalumno(int idalumno) {
        this.id_alumno = idalumno;
    }

    public int getIdcurso() {
        return id_curso;
    }

    public void setIdcurso(int idcurso) {
        this.id_curso = idcurso;
    }

    public String getFechainiciocurso() {
        return fechainiciocurso;
    }

    public void setFechainiciocurso(String fechainiciocurso) {
        this.fechainiciocurso = fechainiciocurso;
    }

    public String getFechafinalcurso() {
        return fechafinalcurso;
    }

    public void setFechafinalcurso(String fechafinalcurso) {
        this.fechafinalcurso = fechafinalcurso;
    }

    public String getFechainscripcion() {
        return fechainscripcion;
    }

    public void setFechainscripcion(String fechainscripcion) {
        this.fechainscripcion = fechainscripcion;
    }

    public int getTerminado() {
        return terminado;
    }

    public void setTerminado(int terminado) {
        this.terminado = terminado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
