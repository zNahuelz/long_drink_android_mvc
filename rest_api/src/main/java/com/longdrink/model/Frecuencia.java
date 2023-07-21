package com.longdrink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "frecuencia")
public class Frecuencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_frecuencia;
    private String nombre;
    private int activo;
}
