package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.longdrink.androidapp.adapter.AdminFragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityAdminBinding;
import com.longdrink.androidapp.databinding.ActivityMainBinding;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        binding.admViewPager.setAdapter(new AdminFragmentAdapter(getSupportFragmentManager()));
        binding.admMenuTabs.setupWithViewPager(binding.admViewPager);
    }
}