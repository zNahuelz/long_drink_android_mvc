package com.longdrink.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "profesor_curso")
@IdClass(ProfesorCurso.class)
public class ProfesorCurso {
    @Id
    @Column(name="id_profesor")
    private int idprofesor;
    @Id
    @Column(name="id_curso")
    private int idcurso;
    private Date fecha_inicio_curso;
    private Date fecha_final_curso;

    public ProfesorCurso(){}

    public ProfesorCurso(int idprofesor, int idcurso, Date fecha_inicio_curso, Date fecha_final_curso) {
        this.idprofesor = idprofesor;
        this.idcurso = idcurso;
        this.fecha_inicio_curso = fecha_inicio_curso;
        this.fecha_final_curso = fecha_final_curso;
    }

    public int getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(int idprofesor) {
        this.idprofesor = idprofesor;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
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
