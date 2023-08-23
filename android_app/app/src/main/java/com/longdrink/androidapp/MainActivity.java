package com.longdrink.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.StudentFragmentAdapter;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQAlumnoUsuario;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQCursoTema;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQInscripcion;
import com.longdrink.androidapp.api_model.SQTema;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.databinding.ActivityMainBinding;
import com.longdrink.androidapp.fragments.CoursesFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "http://10.0.2.2:8080";
    ActivityMainBinding binding;
    StudentFragmentAdapter studentFragmentAdapter;

    int id_usuarioSerizable;

    SQAlumno alumno;
    SQCurso curso;
    SQFrecuencia frecuencia;
    SQTurno turno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        id_usuarioSerizable = (int)getIntent().getSerializableExtra("account_id");
        obtenerAlumnoUsuario(id_usuarioSerizable);

        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout){
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "Guiño guiño!", Toast.LENGTH_SHORT).show();
    }


    public void obtenerAlumnoUsuario(int id_usuario){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQAlumnoUsuario> call = retrofitAPI.getUsuarioAlumno(id_usuario);
        call.enqueue(new Callback<SQAlumnoUsuario>() {
            @Override
            public void onResponse(Call<SQAlumnoUsuario> call, Response<SQAlumnoUsuario> response) {
                SQAlumnoUsuario alusr = response.body();
                obtenerAlumno(alusr.getId_alumno());
            }

            @Override
            public void onFailure(Call<SQAlumnoUsuario> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR AL CONECTAR AL SERVIDOR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerAlumno(int id_alumno){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQAlumno> call = retrofitAPI.obtenerAlumnoPorID(id_alumno);
        call.enqueue(new Callback<SQAlumno>() {
            @Override
            public void onResponse(Call<SQAlumno> call, Response<SQAlumno> response) {
                alumno = response.body();

                obtenerInscripcion(alumno);
            }

            @Override
            public void onFailure(Call<SQAlumno> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR AL CONECTAR AL SERVIDOR", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void obtenerInscripcion(@NonNull SQAlumno alumno){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQInscripcion>> call = retrofitAPI.obtenerInscripcion(alumno.getId_alumno());
        call.enqueue(new Callback<List<SQInscripcion>>() {
            @Override
            public void onResponse(Call<List<SQInscripcion>> call, Response<List<SQInscripcion>> response) {
                if (response.body().size() > 0){
                    SQInscripcion inscripcionActiva = null;
                    for (SQInscripcion ins : response.body()){
                        if (ins.getActivo() == 1){
                            inscripcionActiva = ins;
                            break;
                        }
                    }

                    obtenerCurso(inscripcionActiva);
                }
                else{
                    Log.e("OBTENER_INSCRIPCION_ONRESPONSE", "Se ha regresado null");
                    //Agregando el toolbar al activity
                    setSupportActionBar(binding.mainToolbar);
                    //Instanciando el adaptador
                    studentFragmentAdapter = new StudentFragmentAdapter(getSupportFragmentManager(), alumno, null, null, null, null, null, 0);
                    //Seteando el adaptador al viewpager
                    binding.viewPager.setAdapter(studentFragmentAdapter);
                    //Seteando el viewpager al tablayout
                    binding.menuTabs.setupWithViewPager(binding.viewPager);
                }
            }

            @Override
            public void onFailure(Call<List<SQInscripcion>> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });
    }

    public void obtenerCurso(SQInscripcion inscripcion){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQCurso> call = retrofitAPI.obtenerCursoPorID(inscripcion.getIdcurso());

        call.enqueue(new Callback<SQCurso>() {
            @Override
            public void onResponse(Call<SQCurso> call, Response<SQCurso> response) {
                if (response.body() != null){
                    curso = response.body();
                    obtenerFrecuencia(inscripcion);
                }else{
                    Log.e("OBTENER_CURSO_ONRESPONSE", "Se ha regresado null");
                }
            }

            @Override
            public void onFailure(Call<SQCurso> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });
    }

    public void obtenerFrecuencia(SQInscripcion inscripcion){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQFrecuencia> call = retrofitAPI.obtenerFrecuenciaPorId(inscripcion.getId_frecuencia());

        call.enqueue(new Callback<SQFrecuencia>() {
            @Override
            public void onResponse(Call<SQFrecuencia> call, Response<SQFrecuencia> response) {
                if (response.body() != null){
                    frecuencia = response.body();
                    obtenerTurno(inscripcion);
                }else{
                    Log.e("OBTENER_CURSO_ONRESPONSE", "Se ha regresado null");
                }
            }

            @Override
            public void onFailure(Call<SQFrecuencia> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });
    }

    public void obtenerTurno(SQInscripcion inscripcion){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQTurno> call = retrofitAPI.obtenerTurnoPorId(inscripcion.getId_turno());

        call.enqueue(new Callback<SQTurno>() {
            @Override
            public void onResponse(Call<SQTurno> call, Response<SQTurno> response) {
                if (response.body() != null){
                    turno = response.body();
                    obtenerCursoTema(inscripcion);
                }else{
                    Log.e("OBTENER_CURSO_ONRESPONSE", "Se ha regresado null");
                }
            }

            @Override
            public void onFailure(Call<SQTurno> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });


    }

    public void obtenerCursoTema(SQInscripcion inscripcion){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCursoTema>> call = retrofitAPI.obtenerCursoTema(inscripcion.getIdcurso());

        call.enqueue(new Callback<List<SQCursoTema>>() {
            @Override
            public void onResponse(Call<List<SQCursoTema>> call, Response<List<SQCursoTema>> response) {
                if (response.body() != null){
                    List<SQCursoTema> cursotema = response.body();

                    filtrarTemasPorCurso(cursotema);
                }else{
                    Log.e("OBTENER_CURSO_ONRESPONSE", "Se ha regresado null");
                }
            }

            @Override
            public void onFailure(Call<List<SQCursoTema>> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });
    }

    public void filtrarTemasPorCurso(List<SQCursoTema> cursotema){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQTema>> call = retrofitAPI.listarTodosTemas();

        call.enqueue(new Callback<List<SQTema>>() {
            @Override
            public void onResponse(Call<List<SQTema>> call, Response<List<SQTema>> response) {
                if (response.body() != null){
                    ArrayList<String> listadoNombresTemasFiltrados = new ArrayList<>();
                    ArrayList<String> listadoGuias = new ArrayList<>();
                    cursotema.forEach(cursotema -> {
                        response.body().forEach(tema -> {
                            if (cursotema.getId_tema() == tema.getId_tema()){
                                listadoNombresTemasFiltrados.add(tema.getNombre());
                                listadoGuias.add(tema.getGuia());
                            }
                        });
                    });

                    //Agregando el toolbar al activity
                    setSupportActionBar(binding.mainToolbar);
                    //Instanciando el adaptador
                    studentFragmentAdapter = new StudentFragmentAdapter(getSupportFragmentManager(), alumno, curso, frecuencia,
                            turno, listadoNombresTemasFiltrados, listadoGuias, id_usuarioSerizable);
                    //Seteando el adaptador al viewpager
                    binding.viewPager.setAdapter(studentFragmentAdapter);
                    //Seteando el viewpager al tablayout
                    binding.menuTabs.setupWithViewPager(binding.viewPager);;
                }else{
                    Log.e("OBTENER_CURSO_ONRESPONSE", "Se ha regresado null");
                }
            }

            @Override
            public void onFailure(Call<List<SQTema>> call, Throwable t) {
                Log.e("ONFAILURE", t.getMessage());
            }
        });
    }
}