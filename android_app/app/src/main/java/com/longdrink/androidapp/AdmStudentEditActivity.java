package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.databinding.ActivityAdmStudentEditBinding;
import com.longdrink.androidapp.utils.VTools;

import org.apache.commons.validator.routines.EmailValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmStudentEditActivity extends AppCompatActivity {
    ActivityAdmStudentEditBinding binding;
    final String BASE_URL = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmStudentEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SQAlumno studentData = (SQAlumno) getIntent().getSerializableExtra("student_data");
        fillData(studentData);
        binding.btnEAlumCancelar.setOnClickListener(e -> goBack());
        binding.btnEAlumGuardar.setOnClickListener(e -> UpdateStudent(studentData));
    }

    private void UpdateStudent(SQAlumno studentData){
        SQAlumno s = new SQAlumno(studentData.getId_alumno(),binding.txtEApeMatAlum.getText().toString(),
                binding.txtEApePatAlum.getText().toString(),binding.txtEDNIAlum.getText().toString(),
                binding.txtEEmailAlum.getText().toString(),binding.txtENombreAlumno.getText().toString(),
                studentData.getActivo());
        SQAlumno cleanStudent = processData(s);
        boolean validateData = validateData(cleanStudent);
        if(validateData){
            SendStudent(cleanStudent); //Enviar datos a API.
            HideKeyboard(); //Esconder teclado.
            Handler handler = new Handler(); //Esperar 2 segundos y volver atras.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            },2000);
        }
        else{
            HideKeyboard();
            Snackbar.make(binding.getRoot(), "Error! Debe llenar todos los campos con el formato correcto.",Snackbar.LENGTH_LONG).show();
        }
    }

    private SQAlumno processData(SQAlumno a){
        try{
            return new SQAlumno(a.getId_alumno(),a.getAp_materno().trim().toUpperCase(),
                    a.getAp_paterno().trim().toUpperCase(),a.getDni().trim(),
                    a.getEmail().trim().toUpperCase(),
                    a.getNombre().trim().toUpperCase(),a.getActivo());
        }
        catch(Exception ex){ return new SQAlumno(); }
    }

    private boolean validateData(SQAlumno a){
        boolean nameCheck = a.getNombre().length() >= 1 && a.getNombre().length() <= 50;
        boolean f_nameCheck = a.getAp_paterno().length() >= 1 && a.getAp_paterno().length() <=50;
        boolean m_nameCheck = a.getAp_materno().length() >= 1 && a.getAp_materno().length() <=50;
        boolean dniCheck = a.getDni().length() == 8 || a.getDni().length() == 12;
        boolean emailCheck = a.getEmail().length() >=1 && a.getEmail().length() <= 100;
        EmailValidator validator = EmailValidator.getInstance();
        boolean validEmail = validator.isValid(a.getEmail());
        return nameCheck && f_nameCheck && m_nameCheck && dniCheck && emailCheck && validEmail;
    }

    @SuppressLint("SetTextI18n")
    private void fillData(SQAlumno studentData){
        binding.txtEIDAlumno.setText("Usted esta modificando al alumno: "+studentData.getId_alumno());
        binding.txtENombreAlumno.setText(studentData.getNombre());
        binding.txtEApePatAlum.setText(studentData.getAp_paterno());
        binding.txtEApeMatAlum.setText(studentData.getAp_materno());
        binding.txtEEmailAlum.setText(studentData.getEmail());
        binding.txtEDNIAlum.setText(studentData.getDni());
        //binding.swEActivoAlum.setChecked(studentData.getActivo() == 1);
    }

    private void SendStudent(SQAlumno load){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.actualizarAlumno(load);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() == true){
                    Snackbar.make(binding.getRoot(), "Datos del estudiante actualizados con exito!",Snackbar.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(binding.getRoot(), "Error! Imposible actualizar los datos del estudiante. Intente nuevamente.",Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.EDIT.STUDENT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void goBack(){
        AdmStudentEditActivity.this.finish();
    }

    private void HideKeyboard(){
        View view = AdmStudentEditActivity.this.getCurrentFocus();
        if(view!=null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}