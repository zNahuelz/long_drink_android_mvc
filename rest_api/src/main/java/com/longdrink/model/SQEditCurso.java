package com.longdrink.model;

public class SQEditCurso {
    private int id_curso;
    private float costo;
    private String descripcion;
    private int duracion;
    private String nombre;
    private int activo;
    private String foto;
    private int id_turno;
    private int id_frecuencia;

    public SQEditCurso(){}

    public SQEditCurso(int id_curso, float costo, String descripcion, int duracion, String nombre, int activo, String foto, int id_turno, int id_frecuencia) {
        this.id_curso = id_curso;
        this.costo = costo;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.nombre = nombre;
        this.activo = activo;
        this.foto = foto;
        this.id_turno = id_turno;
        this.id_frecuencia = id_frecuencia;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }

    public int getId_frecuencia() {
        return id_frecuencia;
    }

    public void setId_frecuencia(int id_frecuencia) {
        this.id_frecuencia = id_frecuencia;
    }
}
