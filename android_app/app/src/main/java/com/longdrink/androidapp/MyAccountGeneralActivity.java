package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.api_model.SQUsuario;
import com.longdrink.androidapp.api_model.SQUsuarioProfesor;
import com.longdrink.androidapp.api_model.Usuario;
import com.longdrink.androidapp.databinding.ActivityMyAccountGeneralBinding;

import org.apache.commons.validator.routines.EmailValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAccountGeneralActivity extends AppCompatActivity {
    /*  Este activity puede actualizar contraseña e email de docentes, y unicamente contraseña de administradores... */
    SQUsuarioProfesor userTeacherData;
    SQProfesor teacherData;
    Usuario userData;
    ActivityMyAccountGeneralBinding binding;
    final String BASE_URL = "http://10.0.2.2:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Solo obtengo id usuario 11! (docente) <-Done
        //Traer objeto Usuario para obtener permisos.
        super.onCreate(savedInstanceState);
        binding = ActivityMyAccountGeneralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int accountID = getIntent().getIntExtra("account_data",99);
        Log.e("accID",String.valueOf(accountID));
        getUserData(accountID);
        binding.btnMyAccCancelar.setOnClickListener(e -> goBack());
        binding.btnMyAccGuardar.setOnClickListener(e -> ChangePassword());
    }

    private void getTeacherData(int accountData){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQUsuarioProfesor> call = retrofitAPI.obtenerIDProfesor(accountData);
        call.enqueue(new Callback<SQUsuarioProfesor>() {
            @Override
            public void onResponse(Call<SQUsuarioProfesor> call, Response<SQUsuarioProfesor> response) {
                if(response.isSuccessful()){
                    userTeacherData = response.body();
                    setTeacherData(userTeacherData.getId_profesor());
                }
                else{
                    Snackbar.make(binding.getRoot(), "Error! Imposible recuperar datos de la cuenta.",Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SQUsuarioProfesor> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.TEACHER.MY_ACCOUNT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void setTeacherData(int id_profesor){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQProfesor> call = retrofitAPI.obtenerProfesorPorID(id_profesor);
        call.enqueue(new Callback<SQProfesor>() {
            @Override
            public void onResponse(Call<SQProfesor> call, Response<SQProfesor> response) {
                if(response.isSuccessful()){
                    SQProfesor pD = response.body();
                    teacherData = pD;
                    binding.lblDatosCuenta.setText("NOMBRE: "+pD.getNombre()+"\n\nAPELLIDOS: "+pD.getAp_paterno()+" "+pD.getAp_materno()+"\n\nDNI: "+pD.getDni()+"\n\n");
                    binding.txtMyAccEmail.setText(pD.getEmail());
                }
            }
            @Override
            public void onFailure(Call<SQProfesor> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.TEACHER.MY_ACCOUNT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void getUserData(int usr_id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Usuario> call = retrofitAPI.obtenerUsuarioPorID(usr_id);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful() && response.body().getId_usuario() != 0){
                    userData = response.body();
                    if(userData.getPermisos() == 1){
                        getTeacherData(userData.getId_usuario());
                    }
                    else if (userData.getPermisos() == 2) {
                        binding.lblDatosCuenta.setText("Bienvenido! Su cuenta posee permisos administrativos. Por lo tanto, no posee datos personales asignados. \n\n A continuación, puede cambiar su contraseña.");
                        binding.txtMyAccEmail.setText("ADMIN@LONGDRINK.NET");
                        binding.txtMyAccEmail.setEnabled(false);
                    }
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.TEACHER.MY_ACCOUNT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void sendChangePassword(Usuario u){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.cambiarContrasena(u);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body() == true){
                    HideKeyboard();
                    Snackbar.make(binding.getRoot(), "Felicidades! Ha cambiado las credenciales de su cuenta. Cerrando sesión en 5 segundos...",Snackbar.LENGTH_LONG).show();
                    Handler handler = new Handler(); //Esperar 2 segundos y volver atras.
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishAffinity();
                        }
                    },5000);
                }
                else{
                    HideKeyboard();
                    Snackbar.make(binding.getRoot(), "Ups! Imposible actualizar los datos de su cuenta. Contactar a administración.",Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.TEACHER.MY_ACCOUNT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void sendChangeTeacherEmail(SQProfesor teacherEmail){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Boolean> call = retrofitAPI.actualizarProfesor(teacherEmail);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body() == true){
                    HideKeyboard();
                    Snackbar.make(binding.getRoot(), "E-Mail cambiado con exito! Cerrando sesión en 5 segundos...",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    HideKeyboard();
                    Snackbar.make(binding.getRoot(), "Ups! Imposible actualizar los datos de su cuenta. Contactar a administración.",Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error! Ha fallado la conexión con el servidor.",Snackbar.LENGTH_LONG).show();
                Log.e("[ADMIN.TEACHER.MY_ACCOUNT] onFailure : ",t.getLocalizedMessage().toString());
            }
        });
    }

    private void ChangePassword(){
        String P1 = binding.txtMyAccP1.getText().toString();
        String P2 = binding.txtMyAccP2.getText().toString();
        String email = binding.txtMyAccEmail.getText().toString().trim().toUpperCase();
        boolean emailCheck = email.length() >= 1 && email.length() <= 100;
        EmailValidator validator = EmailValidator.getInstance();
        boolean validEmail = validator.isValid(email);
        if(P1.equals(P2) && P1.length() >=5 && P1.length()<=20 && emailCheck && validEmail){
            userData.setContrasena(P1);
            if(teacherData != null){
                teacherData.setEmail(email);
                sendChangeTeacherEmail(teacherData);
                userData.setContrasena(P1);
                sendChangePassword(userData);
            }
            else{
                userData.setContrasena(P1);     //Si algo no funciona con el cambio de credenciales, revisar aqui.
                sendChangePassword(userData);
            }
        }
        else{
            HideKeyboard();
            Snackbar.make(binding.getRoot(), "Error! Ambas contraseñas deben ser iguales y no exceder 20 caracteres. Su E-Mail debe ser correcto!",Snackbar.LENGTH_LONG).show();
        }
    }

    private void goBack(){
        MyAccountGeneralActivity.this.finish();
    }

    private void HideKeyboard(){
        View view = MyAccountGeneralActivity.this.getCurrentFocus();
        if(view!=null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}