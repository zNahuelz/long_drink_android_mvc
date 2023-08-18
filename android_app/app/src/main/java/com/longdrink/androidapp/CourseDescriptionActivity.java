package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding;
import com.longdrink.androidapp.databinding.ActivityMainBinding;

public class CourseDescriptionActivity extends AppCompatActivity {

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

        binding.courseDescriptionName.setText(cursoSeleccionado.getNombre());
        binding.courseDescriptionPrice.setText("Precio:    S/." + cursoSeleccionado.getCosto());
        binding.courseDescriptionDescription.setText("Descripción: " + cursoSeleccionado.getDescripcion());
        binding.courseDescriptionBack.setOnClickListener(a -> onBackPressed());
        binding.courseDescriptionInscription.setOnClickListener(v -> mostrarInscripcion());
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
}