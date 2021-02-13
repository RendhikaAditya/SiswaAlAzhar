package com.example.siswaal_azhar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siswaal_azhar.activity.IzinActivity;
import com.example.siswaal_azhar.activity.JadwalPelajaranActivity;
import com.example.siswaal_azhar.activity.JadwalUjianActivity;
import com.example.siswaal_azhar.activity.MateriActivity;
import com.example.siswaal_azhar.activity.ProfilActivity;

import com.example.siswaal_azhar.activity.QuisActivity;

import com.example.siswaal_azhar.activity.RaporActivity;

import com.example.siswaal_azhar.activity.TugasActivity;
import com.example.siswaal_azhar.databinding.ActivityMainBinding;
import com.example.siswaal_azhar.util.PrefManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);

        binding.btJadwalPelajaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JadwalPelajaranActivity.class);
                startActivity(intent);
            }
        });

        binding.btJadwalUjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JadwalUjianActivity.class);
                startActivity(intent);
            }
        });

        binding.btIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IzinActivity.class);
                startActivity(intent);
            }
        });

        binding.btMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MateriActivity.class);
                startActivity(intent);
            }
        });

        binding.btTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TugasActivity.class);
                startActivity(intent);
            }
        });

        binding.bioUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        binding.btnQuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuisActivity.class);
                startActivity(intent);
            }
        });

        binding.btRapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RaporActivity.class);

                startActivity(intent);
            }
        });
    }
}
