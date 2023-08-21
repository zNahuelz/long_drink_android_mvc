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
import com.longdrink.androidapp.fragments.CoursesFragment;
import com.longdrink.androidapp.fragments.MyAccountFragment;
import com.longdrink.androidapp.fragments.MyCoursesFragment;

public class StudentFragmentAdapter extends FragmentPagerAdapter {

    private SQAlumno alumno;

    public StudentFragmentAdapter(@NonNull FragmentManager fm, SQAlumno alumno) {

        super(fm);
        this.alumno = alumno;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("alumno", alumno);
        switch (position)
        {
            case 0:
                CoursesFragment cF = new CoursesFragment();
                cF.setArguments(bundle);
                return cF;
            case 1: return new MyCoursesFragment();
            case 2: return new MyAccountFragment();
            default: return new CoursesFragment();
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
