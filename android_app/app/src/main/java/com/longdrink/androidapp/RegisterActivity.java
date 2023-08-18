package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRegisterBinding binding;
    public final String URL_BASE = "http://10.0.2.2:8080/sq/";
    /*TextView nom,apepa,apema,dni,email,pass1,pass2;

    ImageView login;
    Button registrarse;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        /*login = (ImageView) findViewById(R.id.register_back);
        registrarse = (Button) findViewById(R.id.register_button);
        nom = (TextView) findViewById(R.id.register_name);
        apepa = (TextView) findViewById(R.id.register_last_father);
        apema = (TextView) findViewById(R.id.register_last_mother);
        dni = (TextView) findViewById(R.id.register_dni);
        email = (TextView) findViewById(R.id.register_email);
        pass1 = (TextView) findViewById(R.id.register_password);
        pass2 = (TextView) findViewById(R.id.register_password_repeat);
        registrarse.setOnClickListener(this::onClick);*/

        binding.registerButton.setOnClickListener(this::onClick);
        binding.registerBack.setOnClickListener(this::onClick);
        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_back:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            case R.id.register_button:
                //Toast.makeText(this, "Clickaste registrar!", Toast.LENGTH_SHORT).show();
                String nombre = binding.registerName.getText().toString();
                String apePa = binding.registerLastFather.getText().toString();
                String apeMa = binding.registerLastMother.getText().toString();
                String DNI = binding.registerDni.getText().toString();
                String e_mail = binding.registerEmail.getText().toString();
                String contra1 = binding.registerPassword.getText().toString();
                String contra2 = binding.registerPasswordRepeat.getText().toString();
                //String test = nombre + " "+apePa+" "+apeMa+" "+DNI+" "+e_mail+" "+contra1+" "+contra2;
                //Toast.makeText(this, test, Toast.LENGTH_LONG).show();
                /** ***** Validaciones ***** */
                if(nombre.length() == 0 || apePa.length() == 0 || apeMa.length() == 0 || DNI.length() == 0 || e_mail.length() == 0
                || contra1.length() == 0 || contra2.length() == 0){
                    Toast.makeText(this, "ADVERTENCIA: Debes llenar todos los campos para completar tu registro!", Toast.LENGTH_LONG).show();
                }
                else{
                    if(contra1.equals(contra2)){
                        //God... Please be kind.
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build();
                        RetrofitAPI retroAPI = retrofit.create(RetrofitAPI.class);
                        SQRegistro load = new SQRegistro(nombre,apePa,apeMa,DNI,e_mail,contra1,0);
                        Call<SQRegistro> call = retroAPI.registrarMain(load);
                        call.enqueue(new Callback<SQRegistro>() {
                            @Override
                            public void onResponse(Call<SQRegistro> call, Response<SQRegistro> response) {
                                if(response.body().getDni().equals(DNI)){
                                    Toast.makeText(RegisterActivity.this, "Felicidades! Tu registro esta completo, puedes iniciar sesión", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Ups! El Email o DNI ingresados ya se encuentran registrados... Ingresa a la opción recuperar del menú principal.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<SQRegistro> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this,"Error! Falló la comunicación con el servidor.", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
                    }
                    else{
                        Toast.makeText(this, "ADVERTENCIA: Ambas contraseñas deben ser iguales!", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }
}