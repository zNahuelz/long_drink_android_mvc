package com.longdrink.androidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.FrontFrecuenciaTurno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQCursoFrecuencia;
import com.longdrink.androidapp.api_model.SQCursoTurno;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQInscripcion;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.databinding.ActivityInscriptionBinding;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    List<SQTurno> listadoTurnos;
    List<String> listadoTurnosNombres = new ArrayList<>();

    /** VARIABLES FRECUENCIAS */
    List<SQFrecuencia> listadoFrecuencias;
    List<String> listadoFrecuenciasNombres = new ArrayList<>();

    int     id_alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        id_alumno = (int)getIntent().getSerializableExtra("id_alumno");
        setSupportActionBar(binding.inscriptionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.inscriptionToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        binding.inscriptionToolbar.setNavigationOnClickListener(v -> finish());
        obtenerCursos();

        binding.inscriptionSpinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obtenerCursoFrecuencia(position+1);
                obtenerCursoTurno(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.inscriptionButton.setOnClickListener(click -> inscripcion());

        setContentView(binding.getRoot());
    }


    public ArrayAdapter limpiarSpinners(){
        List<String> arrayvacio = new ArrayList<>();
        arrayvacio.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, arrayvacio);
        return adapter;
    }

    public void obtenerCursos(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCurso>> call = retrofitAPI.listarCursos();
        call.enqueue(new Callback<List<SQCurso>>() {
            @Override
            public void onResponse(Call<List<SQCurso>> call, Response<List<SQCurso>> response) {
                listadoCursos = response.body();
                for(SQCurso curso : listadoCursos){
                    listadoCursosNombres.add(curso.getNombre());
                }
                agregarCursoAdapter();

            }

            @Override
            public void onFailure(Call<List<SQCurso>> call, Throwable t) {
                Toast.makeText(InscriptionActivity.this, "ERROR AL CONECTAR CON LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerCursoFrecuencia(int id_curso){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCursoFrecuencia>> call = retrofitAPI.getCursoFrecuenciaAll(id_curso);
        call.enqueue(new Callback<List<SQCursoFrecuencia>>() {
            @Override
            public void onResponse(Call<List<SQCursoFrecuencia>> call, Response<List<SQCursoFrecuencia>> response) {
                obtenerFrecuencias(response.body());
            }

            @Override
            public void onFailure(Call<List<SQCursoFrecuencia>> call, Throwable t) {
                Log.e("OBTENER_CURSO_FRECUENCIA", "NO HAY CONTACTO CON EL SERVIDOR");
            }
        });
    }

    public void obtenerFrecuencias(List<SQCursoFrecuencia> listadoCursoFrecuencia){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQFrecuencia>> call = retrofitAPI.listarFrecuenciasActivas();
        call.enqueue(new Callback<List<SQFrecuencia>>() {
            @Override
            public void onResponse(Call<List<SQFrecuencia>> call, Response<List<SQFrecuencia>> response) {
                listadoFrecuenciasNombres.clear();
                listadoFrecuencias = response.body();
                listadoFrecuencias.forEach(elemento -> {
                    listadoCursoFrecuencia.forEach(e -> {
                        if (elemento.getId_frecuencia() == e.getId_frecuencia()){
                            listadoFrecuenciasNombres.add(elemento.getNombre());
                        }
                    });
                });
                agregarFrecuenciasAdapter();
            }

            @Override
            public void onFailure(Call<List<SQFrecuencia>> call, Throwable t) {
                Log.e("OBTENER_FRECUENCIA", "NO HAY CONTACTO CON EL SERVIDOR");
            }
        });
    }

    public void obtenerCursoTurno(int id_curso){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCursoTurno>> call = retrofitAPI.getCursoTurnoAll(id_curso);
        call.enqueue(new Callback<List<SQCursoTurno>>() {
            @Override
            public void onResponse(Call<List<SQCursoTurno>> call, Response<List<SQCursoTurno>> response) {
                obtenerTurnos(response.body());
            }

            @Override
            public void onFailure(Call<List<SQCursoTurno>> call, Throwable t) {
                Log.e("OBTENER_CURSO_TURNO", "NO HAY CONTACTO CON EL SERVIDOR");
            }
        });
    }


    public void obtenerTurnos(List<SQCursoTurno> listadoCursoTurno){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQTurno>> call = retrofitAPI.listarTurnos();
        call.enqueue(new Callback<List<SQTurno>>() {
            @Override
            public void onResponse(Call<List<SQTurno>> call, Response<List<SQTurno>> response) {
                listadoTurnosNombres.clear();
                listadoTurnos = response.body();
                listadoTurnos.forEach(elemento -> {
                    listadoCursoTurno.forEach(e -> {
                        if (elemento.getId_turno() == e.getId_turno()){
                            listadoTurnosNombres.add(elemento.getNombre());
                        }
                    });
                });
                agregarTurnosAdapter();
            }

            @Override
            public void onFailure(Call<List<SQTurno>> call, Throwable t) {
                Log.e("OBTENER_TURNOS", "NO HAY CONTACTO CON EL SERVIDOR");            }
        });
    }

    private void agregarCursoAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoCursosNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerCourses.setAdapter(adapter);
    }

    private void agregarFrecuenciasAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoFrecuenciasNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerFrecuency.setAdapter(null);
        binding.inscriptionSpinnerFrecuency.setAdapter(adapter);

    }

    private void agregarTurnosAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listadoTurnosNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inscriptionSpinnerTime.setAdapter(null);
        binding.inscriptionSpinnerTime.setAdapter(adapter);
    }


    public void inscripcion(){
        SQCurso cursoSeleccionado = listadoCursos.get(binding.inscriptionSpinnerCourses.getSelectedItemPosition());
        int id_curso = cursoSeleccionado.getId_curso();
        SQFrecuencia frecuenciaSeleccionada = listadoFrecuencias.get(binding.inscriptionSpinnerFrecuency.getSelectedItemPosition());
        SQTurno turnoSeleccionado = listadoTurnos.get(binding.inscriptionSpinnerTime.getSelectedItemPosition());
        //Todo lo relacionado a las fechas
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInscripcion = new Date(System.currentTimeMillis());
        Date fechaInicioClases = fechaInscripcion;
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicioClases);
        calendario.add(Calendar.MONTH, cursoSeleccionado.getDuracion());
        Date fechaFinal = calendario.getTime();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog
                .setMessage("La inscripción será con estos datos: \n-Curso: "
                + cursoSeleccionado.getNombre() + "\n-Frecuencia: " + frecuenciaSeleccionada.getNombre() +
                "\n-Turno: " + turnoSeleccionado.getNombre())
                .setNegativeButton("No", (dialog, which) -> {})
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQInscripcion inscripcion = new SQInscripcion(id_alumno, id_curso, fechaInicioClases, fechaFinal, fechaInscripcion, 0, 1);
                        enviarDatos(inscripcion);
                    }
                }).show();
    }


    public void enviarDatos(SQInscripcion inscripcion){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.guardarInscripcion(inscripcion);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body().equals(true)){
                    Toast.makeText(InscriptionActivity.this, "Inscripcion realizada con éxito", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(InscriptionActivity.this, "Error al ingresar los datos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(InscriptionActivity.this, "PROBLEMAS AL REALIZAR LA INSCRIPCION", Toast.LENGTH_SHORT).show();
                Log.e("ENVIAR_DATOS", "NO HUBO CONEXION CON EL SERVIDOR");
            }
        });
    }

    /*private void obtenerNombresHorarios(){
        for (SQTurno turno: listadoHorarios){
            listadoHorariosNombres.add(turno.getNombre());
        }
    }*/

    /*public void obtenerFrecuencias(){
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


    private void inscripcionAlumno(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.guardarInscripcion();
    }*/

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