package com.longdrink.androidapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.longdrink.androidapp.MainActivity;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.CoursesRecyclerViewAdapter;
import com.longdrink.androidapp.adapter.MyCourseRVAdapter;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQCursoFrecuencia;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQProfesor;
import com.longdrink.androidapp.api_model.SQProfesorCurso;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.databinding.FragmentMyCoursesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCoursesFragment extends Fragment {

    private String BASE_URL = "http://10.0.2.2:8080";

    MyCourseRVAdapter recyclerViewAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MyCoursesFragment() {
        // Required empty public constructor
    }

    /** ***** Variables a utilizar ****** */
    FragmentMyCoursesBinding binding;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCoursesFragment.
     */

    public static MyCoursesFragment newInstance(String param1, String param2) {
        MyCoursesFragment fragment = new MyCoursesFragment();
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
        //return inflater.inflate(R.layout.fragment_my_courses, container, false);
        SQCurso curso = (SQCurso) getArguments().getSerializable("curso");
        SQFrecuencia frecuencia = (SQFrecuencia) getArguments().getSerializable("frecuencia");
        SQTurno turno = (SQTurno) getArguments().getSerializable("turno");
        ArrayList<String> nombresTemas = getArguments().getStringArrayList("temas");
        ArrayList<String> guias = getArguments().getStringArrayList("guias");
        binding = FragmentMyCoursesBinding.inflate(inflater, container, false);

        Glide.with(this)
                .load(curso.getFoto())
                .into(binding.myCourseImage);
        binding.myCourseName.setText(curso.getNombre());
        binding.myCourseDescription.setText(curso.getDescripcion());
        binding.myCourseFrecuency.setText(frecuencia.getNombre());
        binding.myCourseTime.setText(turno.getNombre());
        obtenerCursoProfesor(curso);

        recyclerViewAdapter = new MyCourseRVAdapter(nombresTemas, guias, getContext());
        //Creando al Manager para que el recyclerView se muestre de manera vertical
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //Agregando el adaptador, el manager y el decorador a recyclerView
        binding.myCourseRecyclerView.setAdapter(recyclerViewAdapter);
        binding.myCourseRecyclerView.setLayoutManager(layoutManager);
        binding.myCourseRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }

    public void obtenerCursoProfesor(SQCurso curso){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQProfesorCurso> call = retrofitAPI.obtenerProfesorCurso(curso.getId_curso());
        call.enqueue(new Callback<SQProfesorCurso>() {
            @Override
            public void onResponse(Call<SQProfesorCurso> call, Response<SQProfesorCurso> response) {
                SQProfesorCurso profcur = response.body();

                obtenerProfesro(profcur.getId_profesor());
            }

            @Override
            public void onFailure(Call<SQProfesorCurso> call, Throwable t) {
                Log.e("OBTENER_PROFESORCURSO_ONFAILURE", t.getMessage());
            }
        });
    }

    public void obtenerProfesro(int id_profesor){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SQProfesor> call = retrofitAPI.obtenerProfesorPorID(id_profesor);
        call.enqueue(new Callback<SQProfesor>() {
            @Override
            public void onResponse(Call<SQProfesor> call, Response<SQProfesor> response) {
                SQProfesor profesor = response.body();
                binding.myCourseTeacher.setText(profesor.getNombre() + " " + profesor.getAp_paterno());
            }

            @Override
            public void onFailure(Call<SQProfesor> call, Throwable t) {
                Log.e("OBTENER_PROFESOR_ONFAILURE", t.getMessage());
            }
        });
    }


}