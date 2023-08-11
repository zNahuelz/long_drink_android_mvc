package com.longdrink.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.StudentFragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityMainBinding;
import com.longdrink.androidapp.fragments.CoursesFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    StudentFragmentAdapter studentFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //Agregando el toolbar al activity
        setSupportActionBar(binding.mainToolbar);
        //Instanciando el adaptador
        studentFragmentAdapter = new StudentFragmentAdapter(getSupportFragmentManager());
        //Seteando el adaptador al viewpager
        binding.viewPager.setAdapter(studentFragmentAdapter);
        //Seteando el viewpager al tablayout
        binding.menuTabs.setupWithViewPager(binding.viewPager);
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "Guiño guiño!", Toast.LENGTH_SHORT).show();
    }

}