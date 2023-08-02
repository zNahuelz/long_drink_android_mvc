package com.longdrink.androidapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.longdrink.androidapp.fragments.AdminCoursesFragment;
import com.longdrink.androidapp.fragments.AdminStudentsFragment;
import com.longdrink.androidapp.fragments.AdminTeachersFragment;

public class AdminFragmentAdapter extends FragmentPagerAdapter {

    public AdminFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 1: return new AdminStudentsFragment();
            case 2: return new AdminTeachersFragment();
            default: return new AdminCoursesFragment();
        }
    }

    @Override
    public int getCount() { return 3; }

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
            title = "Estudiantes";
        }
        if (position == 2)
        {
            title = "Docentes";
        }
        return title;
    }
}
