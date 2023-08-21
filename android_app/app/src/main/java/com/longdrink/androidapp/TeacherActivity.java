package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.longdrink.androidapp.adapter.TeacherFragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityTeacherBinding;

public class TeacherActivity extends AppCompatActivity {
    ActivityTeacherBinding binding;
    TeacherFragmentAdapter teacherFragmentAdapter;

    int id_cuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.teacherToolbar);
        teacherFragmentAdapter = new TeacherFragmentAdapter(getSupportFragmentManager());
        binding.teacherViewPager.setAdapter(teacherFragmentAdapter);
        binding.teacherMenuTabs.setupWithViewPager(binding.teacherViewPager);
        setContentView(binding.getRoot());
        id_cuenta = getIntent().getIntExtra("account_id",0);
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
                Intent intent = new Intent(TeacherActivity.this,MyAccountGeneralActivity.class);
                intent.putExtra("account_data",TeacherActivity.this.id_cuenta);
                TeacherActivity.this.startActivity(intent);
                return true;
            case R.id.teacher_logout:
                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}