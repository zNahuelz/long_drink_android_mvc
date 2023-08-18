package com.longdrink.androidapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.CoursesRecyclerViewAdapter;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.databinding.FragmentCoursesBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CoursesFragment() {
        // Required empty public constructor
    }

    /** ***** Variables a utilizar ****** */
    final String BASE_URL = "http://10.0.2.2:8080";
    //RecyclerView recyclerView;
    CoursesRecyclerViewAdapter recyclerViewAdapter;
    FragmentCoursesBinding binding;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursesFragment newInstance(String param1, String param2) {
        CoursesFragment fragment = new CoursesFragment();
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
        //return inflater.inflate(R.layout.fragment_courses, container, false);

        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SQAlumno alumno = (SQAlumno) getArguments().getSerializable("alumno");
        getCursos(retrofit, alumno);
    }

    public void getCursos(Retrofit retrofit, SQAlumno alumno){
        //Obteniendo la clase api
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        //Obteniendo los cursos
        Call<List<SQCurso>> listadoCursos = retrofitAPI.listarCursos();
        listadoCursos.enqueue(new Callback<List<SQCurso>>() {
            @Override
            public void onResponse(Call<List<SQCurso>> call, Response<List<SQCurso>> response) {
                if(response.isSuccessful()){
                    //Instanciando el adaptador
                    recyclerViewAdapter = new CoursesRecyclerViewAdapter(response.body(), getContext(), alumno);
                    //Creando al Manager para que el recyclerView se muestre de manera vertical
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    //Agregando el adaptador, el manager y el decorador a recyclerView
                    binding.recyclerCourses.setAdapter(recyclerViewAdapter);
                    binding.recyclerCourses.setLayoutManager(layoutManager);
                    binding.recyclerCourses.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                }
                else{
                    Log.e("CourseFragment", "onResponse: Llamada fallida");
                }
            }

            @Override
            public void onFailure(Call<List<SQCurso>> call, Throwable t) {
                Log.e("CourseFragment", "onFailure: Conexión fallida");
                Log.e("CourseFragment", t.getLocalizedMessage());
            }
        });
    }
}