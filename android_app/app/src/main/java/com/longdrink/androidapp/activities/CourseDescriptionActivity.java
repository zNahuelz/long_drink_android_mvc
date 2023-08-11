package com.longdrink.androidapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding;
import com.longdrink.androidapp.databinding.ActivityMainBinding;

public class CourseDescriptionActivity extends AppCompatActivity {

    ActivityCourseDescriptionBinding binding;

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
        Glide.with(this)
                .load(cursoSeleccionado.getFoto())
                .into(binding.courseDescriptionImage);
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
}