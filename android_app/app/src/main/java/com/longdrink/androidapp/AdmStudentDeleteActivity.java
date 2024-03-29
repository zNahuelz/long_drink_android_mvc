package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.databinding.ActivityAdmStudentDeleteBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmStudentDeleteActivity extends AppCompatActivity {
    final String BASE_URL = "http://10.0.2.2:8080";
    final String DEFAULT_STUDENT_IMAGE = "https://i.imgur.com/qyJ80RC.png";
    ActivityAdmStudentDeleteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmStudentDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SQAlumno studentData = (SQAlumno) getIntent().getSerializableExtra("student_data");
        fillData(studentData);

        binding.btnDeleteStudent.setOnClickListener(e -> DeleteStudent(studentData));
        binding.btnGoBackStudent.setOnClickListener(e -> goBack());
    }

    @SuppressLint("SetTextI18n")
    public void fillData(SQAlumno studentData){
        binding.txtStudentData.setText("ID ALUMNO: "+studentData.getId_alumno()+"\n\nNOMBRE: "+studentData.getNombre()
                +"\n\nAPELLIDOS: "+studentData.getAp_paterno()+" "+studentData.getAp_materno()+
                "\n\nE-MAIL: "+studentData.getEmail()+"\n\nDNI: "+studentData.getDni());
        Glide.with(this).load(DEFAULT_STUDENT_IMAGE).into(binding.ivDeleteStudent);
    }
    public void DeleteStudent(SQAlumno studentData){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.eliminarAlumnoPorID(studentData.getId_alumno());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body() == true){
                        Snackbar.make(binding.getRoot(), "Alumno eliminado con exito!",Snackbar.LENGTH_LONG).show();
                        goBackWaiting();
                    }
                    else{
                        Snackbar.make(binding.getRoot(), "El alumno seleccionado ya se encuentra deshabilitado!",Snackbar.LENGTH_SHORT).show();
                        goBackWaiting();
                    }
                }
                else{
                    Snackbar.make(binding.getRoot(), "Ups! Tiempo de espera agotado para la conexión al servidor.",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Ups! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.DELETE.STUDENT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }
    public void goBack(){ AdmStudentDeleteActivity.this.finish(); }

    private void goBackWaiting(){
        Handler handler = new Handler(); //Esperar 2 segundos y volver atras.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goBack();
            }
        },2000);
    }
}