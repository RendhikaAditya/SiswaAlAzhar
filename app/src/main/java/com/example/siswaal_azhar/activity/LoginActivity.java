package com.example.siswaal_azhar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.MainActivity;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.databinding.ActivityLoginBinding;
import com.example.siswaal_azhar.databinding.ActivityMainBinding;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    AlertDialog dialog;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        dialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();
        prefManager = new PrefManager(this);

        if(prefManager.getLoginStatus()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        binding.btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogin();
            }
        });

        binding.daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getLogin() {
        dialog.show();
        AndroidNetworking.post(ApiServer.site_url+"login.php")
                .addBodyParameter("username", binding.tvUsername.getText().toString())
                .addBodyParameter("password", binding.password.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONObject data = response.getJSONObject("data");
                                prefManager.setIdUser(data.getString("id_s"));
                                prefManager.setLoginStatus(true);
                                prefManager.setLvl(data.getString("id_kelas"));
                                prefManager.setNamaUser(data.getString("nama_siswa"));
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                Toasty.success(getApplicationContext(),"Berhasil Masuk",Toasty.LENGTH_SHORT, true).show();
                            }else {
                                Toasty.error(getApplicationContext(),"Gagal Masuk", Toasty.LENGTH_SHORT, true).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Toasty.error(getApplicationContext(),"Gagal Masuk", Toasty.LENGTH_SHORT, true).show();
                    }
                });
    }

}
