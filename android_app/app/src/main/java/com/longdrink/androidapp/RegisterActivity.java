package com.longdrink.androidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQRegistro;
import com.longdrink.androidapp.databinding.ActivityRegisterBinding;

import org.apache.commons.validator.routines.EmailValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    public final String URL_BASE = "http://10.0.2.2:8080/sq/";

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

        binding.registerButton.setOnClickListener(e -> Register());
        binding.registerBack.setOnClickListener(e -> goBack());
        setContentView(binding.getRoot());
    }

    private void Register(){
        String nombre = binding.registerName.getText().toString().trim();
        String apePa = binding.registerLastFather.getText().toString().trim();
        String apeMa = binding.registerLastMother.getText().toString().trim();
        String DNI = binding.registerDni.getText().toString().trim();
        String e_mail = binding.registerEmail.getText().toString().trim();
        String contra1 = binding.registerPassword.getText().toString();
        String contra2 = binding.registerPasswordRepeat.getText().toString();
        EmailValidator validator = EmailValidator.getInstance();
        boolean validEmail = validator.isValid(e_mail);
        /** ***** Validaciones ***** */
        if(nombre.length() == 0 || apePa.length() == 0 || apeMa.length() == 0 || DNI.length() == 0 || e_mail.length() == 0
                || contra1.length() == 0 || contra2.length() == 0 || validEmail == false){
            Snackbar.make(binding.getRoot(), "ADVERTENCIA: Debes llenar todos los campos para completar tu registro!",Snackbar.LENGTH_LONG).show();
        }
        else{
            if(contra1.equals(contra2)){
                sendRegister(nombre,apePa,apeMa,DNI,e_mail,contra1);
            }
            else{
                Snackbar.make(binding.getRoot(), "ADVERTENCIA: Ambas contraseñas deben ser iguales!",Snackbar.LENGTH_SHORT).show();
            }

        }
    }
    private void sendRegister(String nombre, String apePa, String apeMa, String DNI,String e_mail,String contra1){
        //God... Please be kind.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retroAPI = retrofit.create(RetrofitAPI.class);
        SQRegistro load = new SQRegistro(nombre,apePa,apeMa,DNI,e_mail,contra1,0);
        String username = load.generarUsername();
        Call<SQRegistro> call = retroAPI.registrarMain(load);
        call.enqueue(new Callback<SQRegistro>() {
            @Override
            public void onResponse(Call<SQRegistro> call, Response<SQRegistro> response) {
                if(response.body().getDni().equals(DNI)){
                    Snackbar.make(binding.getRoot(), "Felicidades! Tu registro esta completo, puedes iniciar sesión",Snackbar.LENGTH_LONG).show();
                    showUsername(username);
                }
                else{
                    Snackbar.make(binding.getRoot(), "Ups! El Email o DNI ingresados ya se encuentran registrados... Ingresa a la opción recuperar del menú principal.",Snackbar.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SQRegistro> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Falló la comunicación con el servidor.",Snackbar.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private void goBack(){
        RegisterActivity.this.finish();
    }

    private void showUsername(String username){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Su nombre de usuario autogenerado es el siguiente: "+username+"\nRecuerde no olvidarlo ni compartirlo con terceros.");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RegisterActivity.this.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}