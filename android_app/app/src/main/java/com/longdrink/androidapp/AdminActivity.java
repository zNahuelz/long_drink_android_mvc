package com.longdrink.androidapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.longdrink.androidapp.adapter.AdminFragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    AdminFragmentAdapter adminFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.adminToolbar);
        adminFragmentAdapter = new AdminFragmentAdapter(getSupportFragmentManager());
        binding.admViewPager.setAdapter(adminFragmentAdapter);
        binding.admMenuTabs.setupWithViewPager(binding.admViewPager);
        setContentView(binding.getRoot());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.admin_myAccount:
                Toast.makeText(this, "WIP! Mi Cuenta.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.admin_logout:
                Toast.makeText(this, "WIP! Cerrar Sesión", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}