package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.databinding.ActivityAdmTeacherDeleteBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmTeacherDeleteActivity extends AppCompatActivity {
    final String BASE_URL = "http://10.0.2.2:8080";

    ActivityAdmTeacherDeleteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmTeacherDeleteBinding.inflate(getLayoutInflater());
        SQProfesor teacherData = (SQProfesor) getIntent().getSerializableExtra("teacher_data");
        binding.txtTeacherData.setText("ID PROFESOR: "+teacherData.getId_profesor()+"\n\nNOMBRE: "
                +teacherData.getNombre()+"\n\nAPELLIDOS: "
                +teacherData.getAp_paterno()+" "+teacherData.getAp_materno()+"\n\nE-MAIL: "+teacherData.getEmail()+
                "\n\nDNI: "+teacherData.getDni());
        setContentView(binding.getRoot());
        Glide.with(this).load(teacherData.getFoto()).into(binding.ivDeleteTeacher);
        binding.btnDeleteTeacher.setOnClickListener(e -> DeleteTeacher(teacherData));
    }

    public void DeleteTeacher(SQProfesor teacherData){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.eliminarProfesorPorID(teacherData.getId_profesor());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body() == true){
                        Snackbar.make(binding.getRoot(), "Profesor eliminado con exito!",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        Snackbar.make(binding.getRoot(), "El profesor seleccionado ya se encuentra deshabilitado!",Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(binding.getRoot(), "Ups! El servidor se niega a responder porque es una tetera.",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Ups! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.DELETE.TEACHER] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }
}