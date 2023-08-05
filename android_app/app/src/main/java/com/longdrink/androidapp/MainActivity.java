package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.longdrink.androidapp.adapter.StudentFragmentAdapter;
import com.longdrink.androidapp.fragments.CoursesFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar myToolBar;
    ViewPager myViewPager;
    TabLayout myTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewPager = findViewById(R.id.viewPager);
        myTableLayout = findViewById(R.id.menu_tabs);
        myToolBar = findViewById(R.id.main_toolbar);

        setSupportActionBar(myToolBar);
        myViewPager.setAdapter(new StudentFragmentAdapter(getSupportFragmentManager()));
        myTableLayout.setupWithViewPager(myViewPager);

        CoursesFragment coursesFragment = new CoursesFragment();
        FragmentManager fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.viewPager, coursesFragment).commit();
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