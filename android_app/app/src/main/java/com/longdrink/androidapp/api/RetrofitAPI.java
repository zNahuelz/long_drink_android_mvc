package com.longdrink.androidapp.api;

import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.api_model.SQUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/sq/login")
    Call<SQUsuario> iniciarSesion(@Body SQUsuario body);

    @POST("/sq/registermain")
    Call<SQRegistro> registrarMain(@Body SQRegistro body);

    @GET("/curso")
    Call<List<SQCurso>> listarCursos();

    @GET("/curso/activos")
    Call<List<SQCurso>> listarCursosActivos();
    @GET("/alumno")
    Call<List<SQAlumno>> listarAlumnos();
    @GET("/alumno/activos")
    Call<List<SQAlumno>> listarAlumnosActivos();

    @GET("/profesor")
    Call<List<SQProfesor>> listarProfesores();

    @GET("/profesor/activos")
    Call<List<SQProfesor>> listarProfesoresActivos();

    @GET("/frecuencia")
    Call<List<SQFrecuencia>> listarFrecuencias();

    @GET("/frecuencia/activos")
    Call<List<SQFrecuencia>> listarFrecuenciasActivas();

    //Individual. localhost:8080/curso/id?id=3
    @GET("/curso/id")
    Call<SQCurso> obtenerCursoPorID(@Query("id") int id);

    @GET("/alumno/id")
    Call<SQAlumno> obtenerAlumnoPorID(@Query("id") int id);

    @GET("/profesor/id")
    Call<SQProfesor> obtenerProfesorPorID(@Query("id") int id);

    //Eliminar.
    @DELETE("/curso/eliminar/id")
    Call<Boolean> eliminarCursoPorID(@Query("id") int id);

    @DELETE("/alumno/eliminar/id")
    Call<Boolean> eliminarAlumnoPorID(@Query("id") int id);

    @DELETE("/profesor/eliminar/id")
    Call<Boolean> eliminarProfesorPorID(@Query("id") int id);
}
