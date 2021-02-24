package com.example.siswaal_azhar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.activity.IzinActivity;
import com.example.siswaal_azhar.activity.JadwalPelajaranActivity;
import com.example.siswaal_azhar.activity.JadwalUjianActivity;
import com.example.siswaal_azhar.activity.MateriActivity;
import com.example.siswaal_azhar.activity.ProfilActivity;
import com.example.siswaal_azhar.activity.QuisActivity;
import com.example.siswaal_azhar.activity.RaporActivity;
import com.example.siswaal_azhar.activity.SppActivity;
import com.example.siswaal_azhar.activity.TugasActivity;
import com.example.siswaal_azhar.databinding.ActivityMainBinding;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    PrefManager prefManager;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();

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
        binding.btSpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SppActivity.class);

                startActivity(intent);
            }
        });

        getDataProfil();
    }

    private void getDataProfil() {
        alertDialog.show();
        AndroidNetworking.post(ApiServer.site_url+"get_user.php")
                .addBodyParameter("id", prefManager.getIdUser())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONArray d = response.getJSONArray("data");
                                JSONObject data = d.getJSONObject(0);
                                binding.namaProfil.setText(data.getString("nama_siswa"));
                                Picasso.get().load(ApiServer.img_siswa+data.getString("foto_siswa")).into(binding.imgProfil);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
