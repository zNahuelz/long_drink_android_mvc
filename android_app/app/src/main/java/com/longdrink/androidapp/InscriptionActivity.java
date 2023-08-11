package com.longdrink.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.longdrink.androidapp.R;
import com.longdrink.androidapp.databinding.ActivityInscriptionBinding;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityInscriptionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());



        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View v){

    }
}