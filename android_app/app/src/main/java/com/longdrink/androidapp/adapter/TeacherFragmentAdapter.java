package com.longdrink.androidapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.longdrink.androidapp.fragments.TeacherCoursesFragment;
import com.longdrink.androidapp.fragments.TeacherScheduleFragment;

public class TeacherFragmentAdapter extends FragmentPagerAdapter {
    public TeacherFragmentAdapter(@NonNull FragmentManager fm) { super(fm); }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new TeacherCoursesFragment();
            case 1: return new TeacherScheduleFragment();
            default: return new TeacherCoursesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Mis Cursos";
        }
        if (position == 1)
        {
            title = "Mi Horario";
        }
        return title;
    }
}
