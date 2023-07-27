package com.longdrink.androidapp.api;

import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.api_model.SQUsuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/sq/login")
    Call<SQUsuario> iniciarSesion(@Body SQUsuario body);

    @POST("/sq/registermain")
    Call<SQRegistro> registrarMain(@Body SQRegistro body);
}
