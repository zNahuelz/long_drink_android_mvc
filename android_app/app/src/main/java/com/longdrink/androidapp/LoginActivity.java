package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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
        binding.loginForgotClick.setOnClickListener(e -> LostPassword());
        setContentView(binding.getRoot());
    }

    private void Login(){
        String usr = binding.username.getText().toString();
        String pass = binding.password.getText().toString();
        if(usr.length() == 0 || pass.length() == 0){
            HideKeyboard();
            Snackbar.make(binding.getRoot(), "ADVERTENCIA: Debe llenar ambos campos!",Snackbar.LENGTH_LONG).show();
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
                    //TODO : Listo para conexión con modulo de alumnos.
                    Snackbar.make(binding.getRoot(), "Sesión iniciada con exito!",Snackbar.LENGTH_SHORT).show();
                    switch(response.body().getPermisos()){
                        case 0:
                            Intent a = new Intent(LoginActivity.this,MainActivity.class);
                            a.putExtra("id_usuario",response.body().getId_usuario());
                            //TODO : Colocar los extras que requieras!
                            startActivity(a);
                            break;
                        case 1:
                            Intent e = new Intent(LoginActivity.this,TeacherActivity.class);
                            e.putExtra("account_id",response.body().getId_usuario());
                            startActivity(e);
                            break;
                        case 2:
                            Intent i = new Intent(LoginActivity.this,AdminActivity.class);
                            i.putExtra("account_id",response.body().getId_usuario());
                            startActivity(i);
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, "Ups! Su cuenta no tiene permisos. Comunicarse con administración.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    HideKeyboard();
                    Snackbar.make(binding.getRoot(), "Usuario o contraseña incorrectos!",Snackbar.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SQUsuario> call, Throwable t) {
                HideKeyboard();
                Snackbar.make(binding.getRoot(), "Error! Falló la comunicación con el servidor.",Snackbar.LENGTH_SHORT).show();
                Log.e("LOGIN: ",t.getLocalizedMessage());
            }
        });
    }
    private void Register(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void LostPassword(){
        startActivity(new Intent(LoginActivity.this, PasswordRecovery.class));
    }
    private void HideKeyboard(){
        View view = LoginActivity.this.getCurrentFocus();
        if(view!=null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}