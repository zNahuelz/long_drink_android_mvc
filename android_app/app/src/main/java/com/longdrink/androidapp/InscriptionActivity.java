package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.databinding.ActivityInscriptionBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InscriptionActivity extends AppCompatActivity {

    private final String BASE_URL = "http://10.0.2.2:8080";
    ActivityInscriptionBinding binding;
    /** VARIABLES CURSOS */
    List<SQCurso> listadoCursos;
    List<String> listadoCursosNombres = new ArrayList<>();
    /** VARIABLES HORARIOS*/
    List<SQTurno> listadoHorarios;
    List<String> listadoHorariosNombres = new ArrayList<>();

    /** VARIABLES FRECUENCIAS */
    List<SQFrecuencia> listadoFrecuencias;
    List<String> listadoFrecuenciasNombres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.inscriptionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.inscriptionToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        binding.inscriptionToolbar.setNavigationOnClickListener(v -> finish());
        obtenerCursos();
        obtenerHorarios();
        obtenerFrecuencias();

        setContentView(binding.getRoot());
    }

    /**  ***** METODOS DE CURSOS ***** */
    public void obtenerCursos(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCurso>> call = retrofitAPI.listarCursos();
        call.enqueue(new Callback<List<SQCurso>>() {
            @Override
            public void onResponse(Call<List<SQCurso>> call, Response<List<SQCurso>> response) {
                listadoCursos = response.body();
                obtenerNombresCursos();
                agregarAdapterCursos();
            }

            @Override
            public void onFailure(Call<List<SQCurso>> call, Throwable t) {
                Toast.makeText(InscriptionActivity.this, "ERROR AL CONECTAR CON LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerNombresCursos(){
        for (SQCurso curso: listadoCursos){
            listadoCursosNombres.add(curso.getNombre());
        }
    }

    private void agregarAdapterCursos(){
        //binding.inscriptionSpinnerCourses.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoCursosNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerCourses.setAdapter(adapter);
    }

    /**  ***** METODOS DE HORAIOS ***** */

    public void obtenerHorarios(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQTurno>> call = retrofitAPI.listarTurnosActivos();
        call.enqueue(new Callback<List<SQTurno>>() {
            @Override
            public void onResponse(Call<List<SQTurno>> call, Response<List<SQTurno>> response) {
                listadoHorarios = response.body();
                obtenerNombresHorarios();
                agregarAdapterHorarios();
            }

            @Override
            public void onFailure(Call<List<SQTurno>> call, Throwable t) {
                Toast.makeText(InscriptionActivity.this, "ERROR AL CONECTAR CON LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerNombresHorarios(){
        for (SQTurno turno: listadoHorarios){
            listadoHorariosNombres.add(turno.getNombre());
        }
    }

    private void agregarAdapterHorarios(){
        //binding.inscriptionSpinnerCourses.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoHorariosNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerTime.setAdapter(adapter);
    }


    /**  ***** METODOS DE FRECUENCIAS ***** */

    public void obtenerFrecuencias(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQFrecuencia>> call = retrofitAPI.listarFrecuencias();
        call.enqueue(new Callback<List<SQFrecuencia>>() {
            @Override
            public void onResponse(Call<List<SQFrecuencia>> call, Response<List<SQFrecuencia>> response) {
                listadoFrecuencias = response.body();
                obtenerNombresFrecuencias();
                agregarAdapterFrecuencias();
            }

            @Override
            public void onFailure(Call<List<SQFrecuencia>> call, Throwable t) {
                Toast.makeText(InscriptionActivity.this, "ERROR AL CONECTAR CON LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerNombresFrecuencias(){
        for (SQFrecuencia frecuencia: listadoFrecuencias){
            listadoFrecuenciasNombres.add(frecuencia.getNombre());
        }
    }

    private void agregarAdapterFrecuencias(){
        //binding.inscriptionSpinnerCourses.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoFrecuenciasNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerFrecuency.setAdapter(adapter);
    }

    /**  ***** METODO DE INSCRIPCION ******  */
    private void inscripcionAlumno(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        //Call<Boolean> call = retrofitAPI.guardarInscripcion();
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getSelectedItem();
        switch (parent.getId())
        {
            case R.id.inscription_spinner_courses:
                Snackbar.make(view, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}