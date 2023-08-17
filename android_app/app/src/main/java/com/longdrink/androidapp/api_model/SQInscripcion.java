package com.longdrink.androidapp.api_model;

import java.util.Date;

public class SQInscripcion {
    private int idalumno;
    private int idcurso;
    private Date fechainiciocurso;
    private Date fechafinalcurso;
    private Date fechainscripcion;
    private int terminado;
    private int activo;

    public SQInscripcion(){}

    public SQInscripcion(int idalumno, int idcurso, Date fechainiciocurso, Date fechafinalcurso, Date fechainscripcion, int terminado, int activo) {
        this.idalumno = idalumno;
        this.idcurso = idcurso;
        this.fechainiciocurso = fechainiciocurso;
        this.fechafinalcurso = fechafinalcurso;
        this.fechainscripcion = fechainscripcion;
        this.terminado = terminado;
        this.activo = activo;
    }

    public int getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(int idalumno) {
        this.idalumno = idalumno;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public Date getFechainiciocurso() {
        return fechainiciocurso;
    }

    public void setFechainiciocurso(Date fechainiciocurso) {
        this.fechainiciocurso = fechainiciocurso;
    }

    public Date getFechafinalcurso() {
        return fechafinalcurso;
    }

    public void setFechafinalcurso(Date fechafinalcurso) {
        this.fechafinalcurso = fechafinalcurso;
    }

    public Date getFechainscripcion() {
        return fechainscripcion;
    }

    public void setFechainscripcion(Date fechainscripcion) {
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
