package com.example.siswaal_azhar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.databinding.ActivityProfilBinding;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class ProfilActivity extends AppCompatActivity {
private ActivityProfilBinding binding;
PrefManager prefManager;
    AlertDialog alertDialog; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();
        AndroidNetworking.initialize(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfilActivity.this);
                alertDialogBuilder.setTitle("Logout dari aplikasi?");
                alertDialogBuilder
                        .setMessage("Klik Ya untuk keluar!")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                prefManager.setIdUser("");
                                prefManager.setLvl("");
                                prefManager.setLoginStatus(false);
                                prefManager.setNamaUser("");
                                Intent intent = new Intent(ProfilActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        getData();
        
    }

    private void getData() {
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
                                binding.nama.setText(data.getString("nama_siswa"));
                                binding.nis.setText(data.getString("username"));
                                binding.txtKelas.setText("Kelas : "+data.getString("nama_kelas")+data.getString("grup_kelas"));
                                binding.txtAlamat.setText("Alamat : "+data.getString("alamat_siswa"));
                                binding.txtGender.setText("Jenis Kelamin : "+data.getString("gender_siswa"));
                                binding.txtTempatLahir.setText("Tempat Lahir : "+data.getString("tempat_lahir_siswa"));
                                binding.txtTglLahir.setText("Tanggal Lahir : "+data.getString("tanggal_lahir_siswa"));
                                binding.txtNoHp.setText("No Hp : "+data.getString("nohp_siswa"));
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
