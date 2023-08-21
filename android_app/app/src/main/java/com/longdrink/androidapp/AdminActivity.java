package com.longdrink.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.longdrink.androidapp.R;
import com.longdrink.androidapp.adapter.AdminFragmentAdapter;
import com.longdrink.androidapp.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    AdminFragmentAdapter adminFragmentAdapter;
    int id_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.adminToolbar);
        adminFragmentAdapter = new AdminFragmentAdapter(getSupportFragmentManager());
        binding.admViewPager.setAdapter(adminFragmentAdapter);
        binding.admMenuTabs.setupWithViewPager(binding.admViewPager);
        setContentView(binding.getRoot());
        id_cuenta = getIntent().getIntExtra("account_id",0);
        Log.e("id_cuenta",String.valueOf(id_cuenta));
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
                Intent intent = new Intent(AdminActivity.this,MyAccountGeneralActivity.class);
                intent.putExtra("account_data",AdminActivity.this.id_cuenta);
                AdminActivity.this.startActivity(intent);
                return true;
            case R.id.admin_logout:
                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}