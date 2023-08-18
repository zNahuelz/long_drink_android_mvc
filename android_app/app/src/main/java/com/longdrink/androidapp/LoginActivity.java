package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQUsuario;
import com.longdrink.androidapp.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  {

    ActivityLoginBinding binding;
    public final String URL_LOGIN = "http://10.0.2.2:8080/sq/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        binding.loginButton.setOnClickListener(e -> Login());
        binding.loginRegisterButton.setOnClickListener(e -> Register());
        setContentView(binding.getRoot());
    }

    private void Login(){
        String usr = binding.username.getText().toString();
        String pass = binding.password.getText().toString();
        if(usr.length() == 0 || pass.length() == 0){
            Toast.makeText(LoginActivity.this, "ADVERTENCIA: Debe llenar ambos campos!", Toast.LENGTH_LONG).show();
        }
        else {
            sendLogin(usr,pass);
        }
    }

    private void sendLogin(String usr, String pass){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_LOGIN).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retroAPI = retrofit.create(RetrofitAPI.class);
        SQUsuario load = new SQUsuario(usr, pass);
        Call<SQUsuario> call = retroAPI.iniciarSesion(load);
        call.enqueue(new Callback<SQUsuario>() {
            @Override
            public void onResponse(Call<SQUsuario> call, Response<SQUsuario> response) {
                if (response.body().getContrasena().equals(pass) && response.body().getNombre_usuario().equals(usr)) {
                    Toast.makeText(LoginActivity.this, "Sesión iniciada con exito!", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    switch(response.body().getPermisos()){
                        case 0:
                            Toast.makeText(LoginActivity.this, "Ir MainActivity (ALUMNO)", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(LoginActivity.this, "Teacher...", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(LoginActivity.this, "ADMIN.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, "Ups! Su cuenta no tiene permisos. Comunicarse con administración.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    //TODO : Pasar ID de usuario (body) al MainActivity, para uso posterior.
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SQUsuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error! Falló la comunicación con el servidor.", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN: ",t.getLocalizedMessage());
            }
        });
    }
    private void Register(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}