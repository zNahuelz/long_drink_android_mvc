package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name="curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_curso;
    private float costo;
    private String descripcion;
    private int duracion;
    private String nombre;
    private int activo;
    private String foto;

    public Curso(){}

    public Curso(int id_curso, float costo, String descripcion, int duracion, String nombre, int activo, String foto) {
        this.id_curso = id_curso;
        this.costo = costo;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.nombre = nombre;
        this.activo = activo;
        this.foto = foto;
    }

    public boolean isEmpty(){
        return this.costo == 0f && this.descripcion == null && this.duracion == 0 && this.nombre == null && this.activo != 0 || this.activo != 1;
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
}
