package com.longdrink.androidapp.api;

import com.longdrink.androidapp.api_model.FrontFrecuenciaTurno;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQAlumnoUsuario;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQCursoFrecuencia;
import com.longdrink.androidapp.api_model.SQCursoTema;
import com.longdrink.androidapp.api_model.SQCursoTurno;
import com.longdrink.androidapp.api_model.SQEditCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQInscripcion;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.api_model.SQProfesorCurso;
import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.api_model.SQTema;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.api_model.SQUsuario;
import com.longdrink.androidapp.api_model.SQUsuarioProfesor;
import com.longdrink.androidapp.api_model.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    //Inicio de SEGURIDAD
    @POST("/sq/login")
    Call<SQUsuario> iniciarSesion(@Body SQUsuario body);
    @POST("/sq/registermain")
    Call<SQRegistro> registrarMain(@Body SQRegistro body);

    @GET("/sq/recuperar_cuenta")
    Call<Boolean> recuperarCuenta(@Query("dni") String dni);
    //Fin de SEGURIDAD.

    //Comienzo de sección CURSOS.
    @GET("/curso")
    Call<List<SQCurso>> listarCursos();
    @GET("/curso/activos")
    Call<List<SQCurso>> listarCursosActivos();
    @GET("/curso/id")
    Call<SQCurso> obtenerCursoPorID(@Query("id") int id);
    @DELETE("/curso/eliminar/id")
    Call<Boolean> eliminarCursoPorID(@Query("id") int id);
    @PUT("/curso/fEditar")
    Call<Boolean> actualizarCursoFull(@Body SQEditCurso body);
    @GET("/curso/turnofrecuencia")
    Call<FrontFrecuenciaTurno> getFrecuenciaTurno(@Query("id") int id);

    @GET("/curso/cursofrecuencia/listar/id_curso")
    Call<List<SQCursoFrecuencia>> getCursoFrecuenciaAll(@Query("id_curso") int id_curso);
    @GET("/curso/cursoturno/listar/id_curso")
    Call<List<SQCursoTurno>> getCursoTurnoAll(@Query("id_curso") int id_curso);
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

    @GET("/usuario/usr_alum/id_usuario")
    Call<SQAlumnoUsuario> getUsuarioAlumno(@Query("id_usr") int id);
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
    @GET("/frecuencia/id")
    Call<SQFrecuencia> obtenerFrecuenciaPorId(@Query("id")int id);
    //Fin FRECUENCIAS

    //Inicio de TURNOS
    @GET("/turno")
    Call<List<SQTurno>> listarTurnos();
    @GET("/turno/activos")
    Call<List<SQTurno>> listarTurnosActivos();
    @GET("/turno/id")
    Call<SQTurno> obtenerTurnoPorId(@Query("id") int id);
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
    @POST("/inscripcion/nuevo")
    Call<Boolean> guardarInscripcion(@Body SQInscripcion inscripcion);
    @GET("/inscripcion/id_alum")
    Call<List<SQInscripcion>> obtenerInscripcion(@Query("id_alum") int id_alum);
    @DELETE("/inscripcion/eliminar")
    Call<Boolean> terminarInscripcion(@Query("id_alum") int id_alum);
    //FIN INSCRIPCION

    //CURSO_PROFESOR
    @GET("/curso/profesorcurso/id_curso")
    Call<SQProfesorCurso> obtenerProfesorCurso(@Query("id_curso") int id_curso);

    //CURSO_TEMA
    @GET("/curso/cursotema/listar/id_curso")
    Call<List<SQCursoTema>> obtenerCursoTema(@Query("id_curso") int id_curso);

    //TEMA
    @GET("/tema")
    Call<List<SQTema>> listarTodosTemas();
}
