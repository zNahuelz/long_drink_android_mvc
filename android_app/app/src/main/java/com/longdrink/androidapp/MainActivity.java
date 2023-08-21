package com.longdrink.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.StudentFragmentAdapter;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQAlumnoUsuario;
import com.longdrink.androidapp.databinding.ActivityMainBinding;
import com.longdrink.androidapp.fragments.CoursesFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        id_usuarioSerizable = (int)getIntent().getSerializableExtra("id_usuario");
        obtenerAlumnoUsuario(id_usuarioSerizable);

        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
                //Agregando el toolbar al activity
                setSupportActionBar(binding.mainToolbar);
                //Instanciando el adaptador
                studentFragmentAdapter = new StudentFragmentAdapter(getSupportFragmentManager(), alumno);
                //Seteando el adaptador al viewpager
                binding.viewPager.setAdapter(studentFragmentAdapter);
                //Seteando el viewpager al tablayout
                binding.menuTabs.setupWithViewPager(binding.viewPager);
            }

            @Override
            public void onFailure(Call<SQAlumno> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR AL CONECTAR AL SERVIDOR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}