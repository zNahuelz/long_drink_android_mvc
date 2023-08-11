package com.longdrink.androidapp;

import android.content.Intent;
import android.os.Bundle;
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
                Intent intent = new Intent(AdminActivity.this,MyAccountGeneralActivity.class);
                //Tipo de cuenta... 1 Docente / 2 Admin. (Traer de Login -> AdminActivity -> MyAccount).
                intent.putExtra("account_data",12); // ID Usuario. (Debe ser el objeto SQUsuario, mismo origen).
                AdminActivity.this.startActivity(intent);
                return true;
            case R.id.admin_logout:
                Toast.makeText(this, "WIP! Cerrar Sesión", Toast.LENGTH_SHORT).show();
                //android.os.Process.killProcess(android.os.Process.myPid()); //TODO : Lleva al login activity cuando todo este agrupado!
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}