package com.longdrink.androidapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.FrontFrecuenciaTurno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQEditCurso;
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

    List<SQFrecuencia> freqList;
    List<SQTurno> turnList;
    String selectedFreq = "";
    String selectedTurn = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmCourseEditBinding.inflate(getLayoutInflater());
        SQCurso courseData = (SQCurso) getIntent().getSerializableExtra("course_data");
        fillData(courseData); //Llenar datos de campos de texto.
        fillSpinners(courseData); //Llenar datos de los spinners.

        binding.spEFrecuenciaCurso.setOnItemSelectedListener(this);
        binding.spETurnoCurso.setOnItemSelectedListener(this);
        binding.btnEGuardar.setOnClickListener(e -> UpdateCourse(courseData));
        binding.btnEAtras.setOnClickListener(e -> goBack());
        setContentView(binding.getRoot());
    }
    private SQCurso processData(SQCurso c){
        try{
            int active = 0;
            if(binding.swEActivoCurso.isChecked()){ active = 1; }
            return new SQCurso(c.getId_curso(),c.getCosto(),
                    c.getDescripcion().trim(),
                    c.getDuracion(),
                    c.getNombre().trim().toUpperCase(),
                    active,c.getFoto());
        }
        catch(Exception ex){ return new SQCurso(); }
    }
    private boolean validateData(SQCurso c){
        boolean nameCheck = c.getNombre().length() >=1 && c.getNombre().length() <=60;
        boolean descCheck = c.getDescripcion().length() >=1 && c.getDescripcion().length() <= 254;
        boolean durationCheck = c.getDuracion() >= 1 && c.getDuracion() <=6;
        boolean priceCheck = c.getCosto() >= 1f && c.getCosto() <=1000f;
        boolean activeCheck = c.getActivo() == 0 || c.getActivo() == 1;
        return nameCheck && descCheck && durationCheck && priceCheck && activeCheck;
    }

    public void UpdateCourse(SQCurso courseData){
        int active = 0;
        int duracion = 0;
        float costo = 0f;
        if(binding.swEActivoCurso.isChecked()){ active = 1; }
        if(binding.txtECostoCurso.getText().toString().length() != 0){ costo = Float.parseFloat(binding.txtECostoCurso.getText().toString()); }
        if(binding.txtEDuracionCurso.getText().toString().length() != 0){ duracion = Integer.parseInt(binding.txtEDuracionCurso.getText().toString()); }
        SQCurso c = new SQCurso(courseData.getId_curso(), costo,
                binding.txtEDescCurso.getText().toString(), duracion,
                binding.txtENombreCurso.getText().toString(),
                active,courseData.getFoto());
        SQCurso cleanCourse = processData(c);
        boolean validateData = validateData(cleanCourse);
        if(validateData){
            int FreqID = 0;
            int TurnID = 0;
            for(SQFrecuencia e : freqList){ if(e.getNombre().equals(selectedFreq)){ FreqID = e.getId_frecuencia(); } }
            for(SQTurno e : turnList){ if(e.getNombre().equals(selectedTurn)){ TurnID = e.getId_turno(); } }
            SQEditCurso load = new SQEditCurso(courseData.getId_curso(),cleanCourse.getCosto(),
                    cleanCourse.getDescripcion(),cleanCourse.getDuracion(),cleanCourse.getNombre(),
                    cleanCourse.getActivo(),cleanCourse.getFoto(),TurnID,FreqID);
            SendEditCall(load);
            HideKeyboard(); //Esconder teclado...
            Handler handler = new Handler(); //Esperar 2 segundos y volver atras.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            },2000);
        }
        else{
            Snackbar.make(binding.getRoot(), "Error! Debe llenar todos los campos con el formato correcto.",Snackbar.LENGTH_LONG).show();
        }
    }
    public void fillData(SQCurso courseData){
        binding.txtEIDCurso.setText("Usted esta modificando el curso: "+courseData.getId_curso());
        binding.txtENombreCurso.setText(courseData.getNombre());
        binding.txtEDescCurso.setText(courseData.getDescripcion());
        binding.txtEDuracionCurso.setText(String.valueOf(courseData.getDuracion()));
        binding.txtECostoCurso.setText(String.valueOf(courseData.getCosto()));
        binding.swEActivoCurso.setChecked(courseData.getActivo() == 1);
    }

    public void fillSpinners(SQCurso courseData){
        //Setear formato de fecha para turnos.
        Gson dateFormat = new GsonBuilder().setDateFormat("HH:mm:ss").create();
        //Retrofit general.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(dateFormat)).build();
        getFrecuencias(retrofit);
        getTurnos(retrofit);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getSelectedItem();
        switch (parent.getId())
        {
            case R.id.sp_EFrecuenciaCurso:
                selectedFreq = parent.getSelectedItem().toString();
                break;
            case R.id.sp_ETurnoCurso:
                selectedTurn = parent.getSelectedItem().toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Snackbar.make(binding.getRoot(), "Ups! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
        Log.e("[ADMIN.EDIT.COURSE] noData : ","Spinners vacios!");
    }

    public void SendEditCall(SQEditCurso load){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.actualizarCursoFull(load);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(Boolean.TRUE.equals(response.body())){
                    Snackbar.make(binding.getRoot(), "Datos del curso actualizados con exito!",Snackbar.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(binding.getRoot(), "Error! Imposible actualizar los datos del curso. Intente nuevamente.",Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.EDIT.COURSE] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    public void getFrecuencias(Retrofit retrofit){
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQFrecuencia>> frequencyList = retrofitAPI.listarFrecuenciasActivas();

        frequencyList.enqueue(new Callback<List<SQFrecuencia>>() {
            @Override
            public void onResponse(Call<List<SQFrecuencia>> call, Response<List<SQFrecuencia>> response) {
                if(response.isSuccessful()){
                    freqList = response.body();
                    for (SQFrecuencia i:freqList) {
                        freqName.add(i.getNombre());
                        //Log.e("FREQUENCY",i.getNombre());
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
        Call<List<SQTurno>> getTurnList = retrofitAPI.listarTurnosActivos();
        getTurnList.enqueue(new Callback<List<SQTurno>>() {
            @Override
            public void onResponse(Call<List<SQTurno>> call, Response<List<SQTurno>> response) {
                if(response.isSuccessful()){
                    turnList = response.body();
                    for(SQTurno i : turnList){
                        turnName.add(i.getNombre());
                        //Log.e("TURN",i.getNombre());
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
    private void HideKeyboard(){
        View view = AdmCourseEditActivity.this.getCurrentFocus();
        if(view!=null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}