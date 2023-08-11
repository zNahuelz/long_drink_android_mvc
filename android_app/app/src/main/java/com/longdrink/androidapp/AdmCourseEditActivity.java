package com.longdrink.androidapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.databinding.ActivityAdmCourseEditBinding;

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
    List<String> freqName = new ArrayList<String>();
    List<String> turnName = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmCourseEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SQCurso courseData = (SQCurso) getIntent().getSerializableExtra("course_data");
        fillData(courseData); //Llenar datos de campos de texto.
        fillSpinners(); //Llenar datos de los spinners.

        //No hace nada. Ni muestra visualmente lo seleccionado.
        binding.spEFrecuenciaCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("algo",String.valueOf(id));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("Nada","No has seleccionado nada....");
            }
        });
        binding.btnEGuardar.setOnClickListener(e -> UpdateCourse());
        binding.btnEAtras.setOnClickListener(e -> goBack());
    }

    public void UpdateCourse(){
        Toast.makeText(AdmCourseEditActivity.this, "WIP!", Toast.LENGTH_SHORT).show();
    }
    public void fillData(SQCurso courseData){
        binding.txtEIDCurso.setText("Usted esta modificando el curso: "+courseData.getId_curso());
        binding.txtENombreCurso.setText(courseData.getNombre());
        binding.txtEDescCurso.setText(courseData.getDescripcion());
        binding.txtEDuracionCurso.setText(String.valueOf(courseData.getDuracion()));
        binding.txtECostoCurso.setText(String.valueOf(courseData.getCosto()));
        binding.swEActivoCurso.setChecked(courseData.getActivo() == 1);
    }

    public void fillSpinners(){
        //Setear formato de fecha para turnos.
        Gson dateFormat = new GsonBuilder().setDateFormat("HH:mm:ss").create();
        //Retrofit general.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(dateFormat)).build();

        getFrecuencias(retrofit);
        getTurnos(retrofit);
        //TODO : Hacer funcionar los spinner... o dropdown, o reemplazarlos por otro control....
        //TODO : Seran visualmente los textos blancos? .-_.-.
        //Adaptador para Spinner de Frecuencia.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdmCourseEditActivity.this,android.R.layout.simple_spinner_dropdown_item,freqName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        binding.spEFrecuenciaCurso.setAdapter(adapter);
        //Fin llenado de datos de frecuencia.

        //Inicio llenado de datos para Turno.
        ArrayAdapter<String> turnAdapter = new ArrayAdapter<String>(AdmCourseEditActivity.this,android.R.layout.simple_spinner_dropdown_item,turnName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        binding.spETurnoCurso.setAdapter(turnAdapter);
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

    public void getTurnos(Retrofit retrofit){
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQTurno>> turnList = retrofitAPI.listarTurnosActivos();
        turnList.enqueue(new Callback<List<SQTurno>>() {
            @Override
            public void onResponse(Call<List<SQTurno>> call, Response<List<SQTurno>> response) {
                if(response.isSuccessful()){
                    List<SQTurno> turnList = response.body();
                    for(SQTurno i : turnList){
                        turnName.add(i.getNombre());
                        Log.e("TURN",i.getNombre());
                    }
                }
                else{
                    Toast.makeText(AdmCourseEditActivity.this, "WIP", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<SQTurno>> call, Throwable t) {
                Toast.makeText(AdmCourseEditActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.e("[ADMIN.COURSE.EDIT]",t.getLocalizedMessage().toString());
            }
        });
    }

    public void goBack(){
        AdmCourseEditActivity.this.finish();
    }

}