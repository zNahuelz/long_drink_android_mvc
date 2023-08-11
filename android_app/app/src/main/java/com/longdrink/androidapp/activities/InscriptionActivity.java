package com.longdrink.androidapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.longdrink.androidapp.R;
import com.longdrink.androidapp.databinding.ActivityInscriptionBinding;

public class InscriptionActivity extends AppCompatActivity {

    ActivityInscriptionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        binding.inscriptionSpinnerCourses.setAdapter();


        setContentView(binding.getRoot());
    }
}