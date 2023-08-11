package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding binding;
    public final String URL_LOGIN = "http://10.0.2.2:8080/sq/";
    /*Button registro;
    Button iniciar_sesion;
    EditText usuario;
    EditText password;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*registro = (Button)findViewById(R.id.login_register_button);
        iniciar_sesion = (Button)findViewById(R.id.login_button);
        usuario = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registro.setOnClickListener(this::onClick);
        iniciar_sesion.setOnClickListener(this::onClick);*/
        binding.loginButton.setOnClickListener(this::onClick);
        binding.loginRegisterButton.setOnClickListener(this::onClick);

        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                String usr = binding.username.getText().toString();
                String pass = binding.password.getText().toString();
                if(usr.length() == 0 || pass.length() == 0){
                    Toast.makeText(LoginActivity.this, "ADVERTENCIA: Debe llenar ambos campos!", Toast.LENGTH_LONG).show();
                }
                else {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_LOGIN).addConverterFactory(GsonConverterFactory.create()).build();
                    RetrofitAPI retroAPI = retrofit.create(RetrofitAPI.class);
                    SQUsuario load = new SQUsuario(usr, pass);
                    Call<SQUsuario> call = retroAPI.iniciarSesion(load);
                    call.enqueue(new Callback<SQUsuario>() {
                        @Override
                        public void onResponse(Call<SQUsuario> call, Response<SQUsuario> response) {
                            if (response.body().getContrasena().equals(pass) && response.body().getNombre_usuario().equals(usr)) {
                                Toast.makeText(LoginActivity.this, "Sesión iniciada con exito!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                //TODO : Pasar ID de usuario (body) al MainActivity, para uso posterior.
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SQUsuario> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Error! Falló la comunicación con el servidor.", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
                break;
            case R.id.login_register_button:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }
}