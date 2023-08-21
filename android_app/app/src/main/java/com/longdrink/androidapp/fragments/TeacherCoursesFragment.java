package com.longdrink.androidapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.longdrink.androidapp.AdmCourseEditActivity;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.AdmCoursesRVAdapter;
import com.longdrink.androidapp.adapter.TCoursesRVAdapter;
import com.longdrink.androidapp.api.RetrofitAPI;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.databinding.FragmentTeacherCoursesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherCoursesFragment extends Fragment {
    final String BASE_URL = "http://10.0.2.2:8080";
    //: Pasar ID del profesor al getCourses para que retorne solo los cursos del profesor.
    //: Hacer cambio en API.
    List<String> freqName = new ArrayList<String>();
    List<String> turnName = new ArrayList<String>();

    List<SQFrecuencia> freqList;
    List<SQTurno> turnList;
    RecyclerView recyclerView;
    FragmentTeacherCoursesBinding binding;
    TCoursesRVAdapter recyclerViewAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public TeacherCoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherCoursesFragment.
     */

    public static TeacherCoursesFragment newInstance(String param1, String param2) {
        TeacherCoursesFragment fragment = new TeacherCoursesFragment();
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
        return inflater.inflate(R.layout.fragment_teacher_courses, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.teacher_recycler_courses);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getCourses(retrofit);
    }

    private void getCourses(Retrofit retrofit){
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<SQCurso>> coursesList = retrofitAPI.listarCursos();
        coursesList.enqueue(new Callback<List<SQCurso>>() {
            @Override
            public void onResponse(Call<List<SQCurso>> call, Response<List<SQCurso>> response) {
                if(response.isSuccessful()){
                    recyclerViewAdapter = new TCoursesRVAdapter(response.body(),getContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                }
                else{
                    Snackbar.make(getView(),"Ups! Imposible recuperar el listado de cursos. Intente más tarde.",Snackbar.LENGTH_LONG).show();
                    Log.e("[TEACHER.COURSES]", "Error al procesar datos del servidor.");
                }
            }
            @Override
            public void onFailure(Call<List<SQCurso>> call, Throwable t) {
                Snackbar.make(getView(),"Error! Tiempo de espera con el servidor agotado.",Snackbar.LENGTH_LONG).show();
                Log.e("[TEACHER.COURSES]", t.getLocalizedMessage().toString());
            }
        });
    }

}