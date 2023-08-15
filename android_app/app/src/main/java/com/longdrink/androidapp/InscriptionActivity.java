package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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

public class InscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String BASE_URL = "http://10.0.2.2:8080";
    ActivityInscriptionBinding binding;
    List<SQFrecuencia> listaFrecuencia;
    List<SQCurso> listadoCursos;
    List<String> listadoCursosNombres = new ArrayList<>();
    List<SQTurno> listaTurno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        obtenerCursos();



        setContentView(binding.getRoot());
    }

    public void obtenerCursos(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCurso>> call = retrofitAPI.listarCursos();
        call.enqueue(new Callback<List<SQCurso>>() {
            @Override
            public void onResponse(Call<List<SQCurso>> call, Response<List<SQCurso>> response) {
                listadoCursos = response.body();
                obtenerNombresCursos();
                agregarAdapter();
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

    private void agregarAdapter(){
        binding.inscriptionSpinnerCourses.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoCursosNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerCourses.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getSelectedItem();
        switch (parent.getId())
        {
            case R.id.inscription_spinner_courses:
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}