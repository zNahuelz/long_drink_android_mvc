package com.longdrink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "profesor_curso")
public class ProfesorCurso {
    @Id
    private int id_profesor;
    @Id
    private int id_curso;
    private Date fecha_inicio_curso;
    private Date fecha_final_curso;

    public ProfesorCurso(){}

    public ProfesorCurso(int id_profesor, int id_curso, Date fecha_inicio_curso, Date fecha_final_curso) {
        this.id_profesor = id_profesor;
        this.id_curso = id_curso;
        this.fecha_inicio_curso = fecha_inicio_curso;
        this.fecha_final_curso = fecha_final_curso;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
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
}
