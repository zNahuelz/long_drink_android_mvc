package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.databinding.ActivityAdmTeacherEditBinding;

import org.apache.commons.validator.routines.EmailValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmTeacherEditActivity extends AppCompatActivity {
    ActivityAdmTeacherEditBinding binding;
    final String BASE_URL = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmTeacherEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SQProfesor teacherData = (SQProfesor) getIntent().getSerializableExtra("teacher_data");
        fillData(teacherData);
        binding.btnEProfCancelar.setOnClickListener(e -> goBack());
        binding.btnEProfGuardar.setOnClickListener(e -> UpdateTeacher(teacherData));
    }

    private void UpdateTeacher(SQProfesor teacherData){
        int active = 0;
        if(binding.swEActivoProf.isChecked()){ active = 1; }
        SQProfesor p = new SQProfesor(teacherData.getId_profesor(),binding.txtEApeMatProf.getText().toString(),
                binding.txtEApePatProf.getText().toString(),binding.txtEDNIProf.getText().toString(),
                binding.txtEEmailProf.getText().toString(),teacherData.getFoto(),
                binding.txtENombreProfesor.getText().toString(),active);
        SQProfesor cleanTeacher = processData(p);
        boolean validateData = validateData(cleanTeacher);
        if (validateData){
            SendTeacher(cleanTeacher); //Enviar datos a API.
            HideKeyboard(); //Esconder teclado.
            Handler handler = new Handler(); //Esperar 2 segundos y volver atras.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            },2000);
        }
        else{
            HideKeyboard();
            Snackbar.make(binding.getRoot(), "Error! Debe llenar todos los campos con el formato correcto.",Snackbar.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void fillData(SQProfesor teacherData){
        binding.txtEIDProfesor.setText("Usted esta modificando al profesor: "+teacherData.getId_profesor());
        binding.txtENombreProfesor.setText(teacherData.getNombre());
        binding.txtEApePatProf.setText(teacherData.getAp_paterno());
        binding.txtEApeMatProf.setText(teacherData.getAp_materno());
        binding.txtEEmailProf.setText(teacherData.getEmail());
        binding.txtEDNIProf.setText(teacherData.getDni());
        binding.swEActivoProf.setChecked(teacherData.getActivo() == 1);
    }

    private SQProfesor processData(SQProfesor p){
        try{
            return new SQProfesor(p.getId_profesor(),p.getAp_materno().trim().toUpperCase(),
                    p.getAp_paterno().trim().toUpperCase(),p.getDni().trim(),
                    p.getEmail().trim().toUpperCase(),p.getFoto(),
                    p.getNombre().trim().toUpperCase(),p.getActivo());
        }
        catch(Exception ex){ return new SQProfesor(); }
    }

    private boolean validateData(SQProfesor p){
        boolean nameCheck = p.getNombre().length() >= 1 && p.getNombre().length() <=50;
        boolean f_NameCheck = p.getAp_paterno().length() >= 1 && p.getAp_paterno().length() <=50;
        boolean m_nameCheck = p.getAp_materno().length() >= 1 && p.getAp_materno().length() <=50;
        boolean dniCheck = p.getDni().length() == 8 || p.getDni().length() == 12;
        boolean emailCheck = p.getEmail().length() >= 1 && p.getEmail().length() <= 100;
        EmailValidator validator = EmailValidator.getInstance();
        boolean validEmail = validator.isValid(p.getEmail());
        return nameCheck && f_NameCheck && m_nameCheck && dniCheck && emailCheck && validEmail;
    }

    private void SendTeacher(SQProfesor load){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.actualizarProfesor(load);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() == true){
                    Snackbar.make(binding.getRoot(), "Datos del profesor actualizados con exito!",Snackbar.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(binding.getRoot(), "Error! Imposible actualizar los datos del profesor. Intente nuevamente.",Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.EDIT.TEACHER] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void goBack(){ AdmTeacherEditActivity.this.finish(); }

    private void HideKeyboard(){
        View view = AdmTeacherEditActivity.this.getCurrentFocus();
        if(view!=null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}