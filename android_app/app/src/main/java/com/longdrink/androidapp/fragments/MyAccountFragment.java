package com.longdrink.androidapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longdrink.androidapp.PasswordRecovery;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCursoTema;
import com.longdrink.androidapp.api_model.SQUsuario;
import com.longdrink.androidapp.api_model.Usuario;
import com.longdrink.androidapp.databinding.FragmentMyAccountBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    private final String BASE_URL = "http://10.0.2.2:8080";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /** ***** Variables a utilizar ****** */
    FragmentMyAccountBinding binding;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */

    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_account, container, false);
        SQAlumno alumno = (SQAlumno) getArguments().getSerializable("alumno");
        int id_user =  getArguments().getInt("id_user");
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        binding.myAccountFullname.setText(alumno.getNombre() +  " " + alumno.getAp_paterno() + " " + alumno.getAp_materno());
        binding.myAccountEmail.setText(alumno.getEmail());


        binding.myAccountFullname.setEnabled(false);
        binding.myAccountEmail.setEnabled(false);
        binding.myAccountUsername.setEnabled(false);
        binding.myAccountPassword.setEnabled(false);

        binding.myAccountFullname.setFocusable(false);
        binding.myAccountEmail.setFocusable(false);
        binding.myAccountUsername.setFocusable(false);
        binding.myAccountPassword.setFocusable(false);

        binding.myAccountFullname.setCursorVisible(false);
        binding.myAccountEmail.setCursorVisible(false);
        binding.myAccountUsername.setCursorVisible(false);
        binding.myAccountPassword.setCursorVisible(false);

        binding.myAccountFullname.setBackgroundColor(Color.TRANSPARENT);
        binding.myAccountEmail.setBackgroundColor(Color.TRANSPARENT);
        binding.myAccountUsername.setBackgroundColor(Color.TRANSPARENT);
        binding.myAccountPassword.setBackgroundColor(Color.TRANSPARENT);

        binding.myAccountChangePassword.setOnClickListener(v -> recuperacionContrasena());

        obtenerUsuario(id_user);
        return binding.getRoot();
    }

    public void obtenerUsuario(int id_user){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Usuario> call = retrofitAPI.obtenerUsuarioPorID(id_user);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.body() != null){
                    binding.myAccountPassword.setText(response.body().getContrasena());
                    binding.myAccountUsername.setText(response.body().getNombre_usuario());
                }else{
                    Log.e("OBTENER_USUARIO_RESPONSE", "Se ha regresado null");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("OBTENER_USUARIO_ONFAILURE", t.getMessage());
            }
        });
    }

    public void recuperacionContrasena(){
        Intent intent = new Intent(getContext(), PasswordRecovery.class);
        startActivity(intent);
    }
}