package com.longdrink.androidapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.longdrink.androidapp.fragments.CoursesFragment;
import com.longdrink.androidapp.fragments.MyAccountFragment;
import com.longdrink.androidapp.fragments.MyCoursesFragment;

public class StudentFragmentAdapter extends FragmentPagerAdapter {

    public StudentFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
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
            title = "Mis Cursos";
        }
        if (position == 2)
        {
            title = "Mi Cuenta";
        }

        return title;
    }
}
