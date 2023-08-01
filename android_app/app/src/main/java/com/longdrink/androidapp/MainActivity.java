package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.longdrink.androidapp.adapter.FragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolBar);

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.menuTabs.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "Guiño guiño!", Toast.LENGTH_SHORT).show();
    }
}