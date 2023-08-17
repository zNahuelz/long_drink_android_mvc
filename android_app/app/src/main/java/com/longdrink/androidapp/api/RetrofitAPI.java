package com.longdrink.androidapp.api;

import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQInscripcion;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.api_model.SQUsuario;
import com.longdrink.androidapp.api_model.SQUsuarioProfesor;
import com.longdrink.androidapp.api_model.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    //Inicio de LOGIN y REGISTRO
    @POST("/sq/login")
    Call<SQUsuario> iniciarSesion(@Body SQUsuario body);
    @POST("/sq/registermain")
    Call<SQRegistro> registrarMain(@Body SQRegistro body);
    //Fin de LOGIN y REGISTRO.

    //Comienzo de sección CURSOS.
    @GET("/curso")
    Call<List<SQCurso>> listarCursos();
    @GET("/curso/activos")
    Call<List<SQCurso>> listarCursosActivos();
    @GET("/curso/id")
    Call<SQCurso> obtenerCursoPorID(@Query("id") int id);
    @DELETE("/curso/eliminar/id")
    Call<Boolean> eliminarCursoPorID(@Query("id") int id);
    //Fin CURSOS.

    //Inicio ALUMNOS.
    @GET("/alumno")
    Call<List<SQAlumno>> listarAlumnos();
    @GET("/alumno/activos")
    Call<List<SQAlumno>> listarAlumnosActivos();
    @GET("/alumno/id")
    Call<SQAlumno> obtenerAlumnoPorID(@Query("id") int id);
    @DELETE("/alumno/eliminar/id")
    Call<Boolean> eliminarAlumnoPorID(@Query("id") int id);
    @PUT("/alumno/editar")
    Call<Boolean> actualizarAlumno(@Body SQAlumno a);
    //Fin ALUMNOS


    //Comienzo PROFESORES
    @GET("/profesor")
    Call<List<SQProfesor>> listarProfesores();
    @GET("/profesor/activos")
    Call<List<SQProfesor>> listarProfesoresActivos();
    @PUT("/profesor/editar")
    Call<Boolean> actualizarProfesor(@Body SQProfesor p);
    @DELETE("/profesor/eliminar/id")
    Call<Boolean> eliminarProfesorPorID(@Query("id") int id);
    @GET("/profesor/id")
    Call<SQProfesor> obtenerProfesorPorID(@Query("id") int id);
    //FIN PROFESORES

    //Inicio de FRECUENCIAS
    @GET("/frecuencia")
    Call<List<SQFrecuencia>> listarFrecuencias();
    @GET("/frecuencia/activos")
    Call<List<SQFrecuencia>> listarFrecuenciasActivas();
    //Fin FRECUENCIAS

    //Inicio de TURNOS
    @GET("/turno/activos")
    Call<List<SQTurno>> listarTurnosActivos();
    //Fin de TURNOS

    //Inicio de USUARIOS
    @GET("/usuario/usr_prof/id")
    Call<SQUsuarioProfesor> obtenerIDProfesor(@Query("id_usr") int id_usr);
    @GET("/usuario/id_usuario")
    Call<Usuario> obtenerUsuarioPorID(@Query("id_usuario") int id_usuario);
    @PUT("/usuario/editar")
    Call<Boolean> cambiarContrasena(@Body Usuario user);
    //Fin de USUARIOS.

    //INSCRIPCION USUARIOS
    @POST("/inscripcion")
    Call<Boolean> guardarInscripcion(@Body SQInscripcion inscripcion);
}
