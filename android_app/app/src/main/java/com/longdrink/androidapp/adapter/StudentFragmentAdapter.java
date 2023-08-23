package com.longdrink.androidapp.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.longdrink.androidapp.api_model.SQAlumno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQTema;
import com.longdrink.androidapp.api_model.SQTurno;
import com.longdrink.androidapp.fragments.CoursesFragment;
import com.longdrink.androidapp.fragments.MyAccountFragment;
import com.longdrink.androidapp.fragments.MyCoursesFragment;
import com.longdrink.androidapp.fragments.NoCourseFragment;

import java.util.ArrayList;
import java.util.List;

public class StudentFragmentAdapter extends FragmentPagerAdapter {

    private SQAlumno alumno;
    private SQCurso curso;
    private SQFrecuencia frecuencia;
    private SQTurno turno;
    private ArrayList<String> nombresTemas;
    private ArrayList<String> guias;
    private int id_user;

    public StudentFragmentAdapter(@NonNull FragmentManager fm, SQAlumno alumno ,SQCurso curso,
                                  SQFrecuencia frecuencia, SQTurno turno, ArrayList<String> temas, ArrayList<String> guias,
                                  int id_user) {

        super(fm);
        this.alumno = alumno;
        this.curso = curso;
        this.frecuencia = frecuencia;
        this.turno = turno;
        this.nombresTemas = temas;
        this.guias = guias;
        this.id_user = id_user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        switch (position)
        {
            case 0:
                bundle.putSerializable("alumno", alumno);
                CoursesFragment cF = new CoursesFragment();
                cF.setArguments(bundle);
                return cF;
            case 1:
                if (curso == null){
                    return new NoCourseFragment();
                }
                else{
                    bundle.putSerializable("curso", curso);
                    bundle.putSerializable("frecuencia", frecuencia);
                    bundle.putSerializable("turno", turno);
                    bundle.putStringArrayList("temas", nombresTemas);
                    bundle.putStringArrayList("guias", guias);
                    MyCoursesFragment mCF = new MyCoursesFragment();
                    mCF.setArguments(bundle);
                    return mCF;
                }
            case 2:
                bundle.putSerializable("alumno", alumno);
                bundle.putInt("id_user", id_user);
                MyAccountFragment myAc = new MyAccountFragment();
                myAc.setArguments(bundle);
                return myAc;
            default:
                bundle.putSerializable("alumno", alumno);
                bundle.putInt("id_user", id_user);
                CoursesFragment cf = new CoursesFragment();
                cf.setArguments(bundle);
                return cf;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if (position == 0)
        {
            title = "Cursos";
        }
        if (position == 1)
        {
            title = "Mi Curso";
        }
        if (position == 2)
        {
            title = "Mi Cuenta";
        }

        return title;
    }


}
