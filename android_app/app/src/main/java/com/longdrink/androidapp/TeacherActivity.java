package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.longdrink.androidapp.adapter.TeacherFragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityTeacherBinding;

public class TeacherActivity extends AppCompatActivity {
    ActivityTeacherBinding binding;
    TeacherFragmentAdapter teacherFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.teacherToolbar);
        teacherFragmentAdapter = new TeacherFragmentAdapter(getSupportFragmentManager());
        binding.teacherViewPager.setAdapter(teacherFragmentAdapter);
        binding.teacherMenuTabs.setupWithViewPager(binding.teacherViewPager);
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.teacher_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.teacher_myAccount:
                Toast.makeText(this, "WIP! Mi cuenta.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.teacher_logout:
                Toast.makeText(this, "WIP! Cerrar Sesión", Toast.LENGTH_SHORT).show();
                //android.os.Process.killProcess(android.os.Process.myPid()); //TODO : Lleva al login activity cuando todo este agrupado!
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}