package com.longdrink.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.StudentFragmentAdapter;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQInscripcion;
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding;
import com.longdrink.androidapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseDescriptionActivity extends AppCompatActivity {

    String BASE_URL = "http://10.0.2.2:8080";
    ActivityCourseDescriptionBinding binding;

    SQAlumno alumno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Obteniendo datos del fragment
        SQCurso cursoSeleccionado = (SQCurso) getIntent().getSerializableExtra("curso");
        //Instanciando vinculacion de vistas
        binding = ActivityCourseDescriptionBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.courseDescriptionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.courseDescriptionToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        binding.courseDescriptionToolbar.setNavigationOnClickListener(v -> regresarMain());

        binding.courseDescriptionName.setText(cursoSeleccionado.getNombre());
        binding.courseDescriptionPrice.setText("Precio:    S/." + cursoSeleccionado.getCosto());
        binding.courseDescriptionDescription.setText("Descripción: " + cursoSeleccionado.getDescripcion());
        binding.courseDescriptionInscription.setOnClickListener(v -> {
            obtenerInscripcion(alumno);

        });
        Glide.with(this)
                .load(cursoSeleccionado.getFoto())
                .into(binding.courseDescriptionImage);
        alumno = (SQAlumno) getIntent().getSerializableExtra("alumno");
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void mostrarInscripcion(){
        Intent intent = new Intent(CourseDescriptionActivity.this, InscriptionActivity.class);
        intent.putExtra("id_alumno", alumno.getId_alumno());
        startActivity(intent);
    }

    public void regresarMain(){
        Intent intent = new Intent(CourseDescriptionActivity.this, MainActivity.class);
        intent.putExtra("account_id", alumno.getId_alumno());
        startActivity(intent);
    }

    public void obtenerInscripcion(@NonNull SQAlumno alumno){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQInscripcion>> call = retrofitAPI.obtenerInscripcion(alumno.getId_alumno());
        call.enqueue(new Callback<List<SQInscripcion>>() {
            @Override
            public void onResponse(Call<List<SQInscripcion>> call, Response<List<SQInscripcion>> response) {
                if (response.body().size() > 0){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CourseDescriptionActivity.this);
                    alertDialog.setMessage("Usted ya se ha inscrito en un curso, termínelo o retírese del curso para\n" +
                                    "poder inscribirse a otro")
                            .setNeutralButton("OK", (v, a) -> {}).show();
                }
                else{
                    Log.e("OBTENERINSCRIPCINO_ONRESPONSE", "NO HAY INSCRIPCION");
                    mostrarInscripcion();
                }
            }

            @Override
            public void onFailure(Call<List<SQInscripcion>> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });

    }


}