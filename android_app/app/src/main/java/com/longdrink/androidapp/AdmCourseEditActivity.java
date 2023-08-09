package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.databinding.ActivityAdmCourseEditBinding;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmCourseEditActivity extends AppCompatActivity {
    final String BASE_URL = "http://10.0.2.2:8080";
    ActivityAdmCourseEditBinding binding;

    Context context;
    ArrayAdapter<SQFrecuencia> frequencyAdapter;

    List<String> freqName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmCourseEditBinding.inflate(getLayoutInflater());
        SQCurso courseData = (SQCurso) getIntent().getSerializableExtra("course_data");
        binding.txtEIDCurso.setText("Usted esta modificando el curso: "+courseData.getId_curso());
        binding.txtENombreCurso.setText(courseData.getNombre());
        binding.txtEDescCurso.setText(courseData.getDescripcion());
        binding.txtEDuracionCurso.setText(String.valueOf(courseData.getDuracion()));
        binding.txtECostoCurso.setText(String.valueOf(courseData.getCosto()));
        if(courseData.getActivo() == 1){
            binding.swEActivoCurso.setChecked(true);
        }
        else{
            binding.swEActivoCurso.setChecked(false);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        getFrecuencias(retrofit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdmCourseEditActivity.this,android.R.layout.simple_spinner_dropdown_item,freqName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEFrecuenciaCurso.setAdapter(adapter);
        binding.spEFrecuenciaCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdmCourseEditActivity.this, binding.spEFrecuenciaCurso.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setContentView(binding.getRoot());
        //Continua, gordo!
    }

    public void getFrecuencias(Retrofit retrofit){
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQFrecuencia>> frequencyList = retrofitAPI.listarFrecuenciasActivas();

        frequencyList.enqueue(new Callback<List<SQFrecuencia>>() {
            @Override
            public void onResponse(Call<List<SQFrecuencia>> call, Response<List<SQFrecuencia>> response) {
                if(response.isSuccessful()){
                    List<SQFrecuencia> freqList = response.body();
                    for (SQFrecuencia i:freqList) {
                        freqName.add(i.getNombre());
                        Log.e("FREQUENCY",i.getNombre());
                    }

                }
                else{
                    Toast.makeText(AdmCourseEditActivity.this, "Ups!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SQFrecuencia>> call, Throwable t) {
                Toast.makeText(AdmCourseEditActivity.this, "onFailure...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}