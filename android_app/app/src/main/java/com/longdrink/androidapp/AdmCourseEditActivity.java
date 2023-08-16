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

public class AdmCourseEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final String BASE_URL = "http://10.0.2.2:8080";
    ActivityAdmCourseEditBinding binding;
    List<String> freqName = new ArrayList<String>();
    List<String> turnName = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmCourseEditBinding.inflate(getLayoutInflater());


        SQCurso courseData = (SQCurso) getIntent().getSerializableExtra("course_data");
        fillData(courseData); //Llenar datos de campos de texto.
        fillSpinners(); //Llenar datos de los spinners.

        //No hace nada. Ni muestra visualmente lo seleccionado.
        binding.spEFrecuenciaCurso.setOnItemSelectedListener(this);
        binding.btnEGuardar.setOnClickListener(e -> UpdateCourse());
        binding.btnEAtras.setOnClickListener(e -> goBack());
        setContentView(binding.getRoot());
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

        //Fin llenado de datos de frecuencia.

        //Inicio llenado de datos para Turno.

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getSelectedItem();
        switch (parent.getId())
        {
            case R.id.sp_EFrecuenciaCurso:
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp_ETurnoCurso:
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "No has seleccionado nada!", Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdmCourseEditActivity.this,android.R.layout.simple_list_item_1,freqName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter.notifyDataSetChanged();
                    binding.spEFrecuenciaCurso.setAdapter(adapter);
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
                    ArrayAdapter<String> turnAdapter = new ArrayAdapter<>(AdmCourseEditActivity.this,android.R.layout.simple_list_item_1,turnName);
                    turnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    turnAdapter.notifyDataSetChanged();
                    binding.spETurnoCurso.setAdapter(turnAdapter);
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