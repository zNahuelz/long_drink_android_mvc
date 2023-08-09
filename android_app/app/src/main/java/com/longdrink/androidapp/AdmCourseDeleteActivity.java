package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.databinding.ActivityAdmCourseDeleteBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmCourseDeleteActivity extends AppCompatActivity {
    final String BASE_URL = "http://10.0.2.2:8080";

    ActivityAdmCourseDeleteBinding binding;
    Context context;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmCourseDeleteBinding.inflate(getLayoutInflater());
        SQCurso courseData = (SQCurso) getIntent().getSerializableExtra("course_data");

        binding.txtCourseData.setText("ID CURSO: "+courseData.getId_curso()+
                "\n\nNOMBRE: "+courseData.getNombre()
                +"\n\nDESCRIPCIÓN: "+courseData.getDescripcion()+
                "\n\nDURACIÓN: "+courseData.getDuracion()+" SEMANAS\n\n"+
                "COSTO: "+courseData.getCosto()+" Soles\n\n"
        );
        setContentView(binding.getRoot());
        Glide.with(this).load(courseData.getFoto()).into(binding.ivDeleteCourse);

        binding.btnDeleteCourse.setOnClickListener(e -> DeleteCourse(courseData));
    }

    public void DeleteCourse(SQCurso courseData){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.eliminarCursoPorID(courseData.getId_curso());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body() == true){
                        Snackbar.make(binding.getRoot(), "Curso eliminado con exito!",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        Snackbar.make(binding.getRoot(), "El curso seleccionado ya se encuentra deshabilitado!",Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(AdmCourseDeleteActivity.this, "Ups! El servidor se niega a responder porque es una tetera.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(AdmCourseDeleteActivity.this, "Ups! Ha fallado la conexión con el servidor.", Toast.LENGTH_SHORT).show();
                Log.e("[ADMIN.DELETE.COURSE] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }
}