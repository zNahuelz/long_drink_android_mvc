package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {
    @Id
    private int id_alumno;
    @Id
    private int id_curso;
    private Date fecha_inicio_curso;
    private Date fecha_final_curso;
    private Date fecha_inscripcion;
    private int terminado;
    private int activo;

    public Inscripcion(){}

    public Inscripcion(int id_alumno, int id_curso, Date fecha_inicio_curso, Date fecha_final_curso, Date fecha_inscripcion, int terminado, int activo) {
        this.id_alumno = id_alumno;
        this.id_curso = id_curso;
        this.fecha_inicio_curso = fecha_inicio_curso;
        this.fecha_final_curso = fecha_final_curso;
        this.fecha_inscripcion = fecha_inscripcion;
        this.terminado = terminado;
        this.activo = activo;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
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

    public Date getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(Date fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
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
