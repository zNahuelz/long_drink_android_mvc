package com.longdrink.androidapp.api;

import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.api_model.SQUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/sq/login")
    Call<SQUsuario> iniciarSesion(@Body SQUsuario body);

    @POST("/sq/registermain")
    Call<SQRegistro> registrarMain(@Body SQRegistro body);

    @GET("/curso")
    Call<List<SQCurso>> listarCursos();
}
