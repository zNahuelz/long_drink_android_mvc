package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.databinding.ActivityPasswordRecoveryBinding;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasswordRecovery extends AppCompatActivity {
    ActivityPasswordRecoveryBinding binding;

    final String BASE_URL = "http://10.0.2.2:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordRecoveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnPrCancel.setOnClickListener(e -> goBack());
        binding.btnPrRecover.setOnClickListener(e -> sendPasswordRecovery());
    }

    private String processDNI(){
        try{
            return binding.txtPrDni.getText().toString().trim();
        }
        catch(Exception ex){ return ""; }
    }

    private boolean checkDNI(String dni){
        return dni.length() >= 8 && dni.length() <= 12;
    }

    private void sendPasswordRecovery(){
        String dni = processDNI();
        boolean checkDni = checkDNI(dni);
        if(checkDni){
            HideKeyboard();
            Snackbar.make(binding.getRoot(), "Solicitud en proceso, tiempo de espera máximo de un minuto. Espere, por favor.",Snackbar.LENGTH_LONG).show();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Boolean> call = retrofitAPI.recuperarCuenta(dni);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful() && response.body() != null){
                        HideKeyboard();
                        Snackbar.make(binding.getRoot(), "Solicitud enviada con exito! Recuerde revisar su e-mail.",Snackbar.LENGTH_LONG).show();
                        goBackWaiting();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("[PASSWORD.RECOVERY]: ",t.getLocalizedMessage());
                }
            });

        }
        else{
            HideKeyboard();
            Snackbar.make(binding.getRoot(), "Error! Debe llenar el campo DNI con el formato correcto.",Snackbar.LENGTH_LONG).show();
        }
    }

    private void goBack(){
        PasswordRecovery.this.finish();
    }

    private void goBackWaiting(){
        Handler handler = new Handler(); //Esperar 2 segundos y volver atras.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goBack();
            }
        },2000);
    }
    private void HideKeyboard(){
        View view = PasswordRecovery.this.getCurrentFocus();
        if(view!=null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}