package com.example.siswaal_azhar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.adapter.AdapterJadwal;
import com.example.siswaal_azhar.adapter.AdapterMateri;
import com.example.siswaal_azhar.databinding.ActivityMateriBinding;
import com.example.siswaal_azhar.model.ModelJadwal;
import com.example.siswaal_azhar.model.ModelMateri;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MateriActivity extends AppCompatActivity {
    private ActivityMateriBinding binding;
    PrefManager prefManager;
    AlertDialog dialog;
    List<ModelMateri> materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMateriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        dialog = new SpotsDialog.Builder().setContext(MateriActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();

        getMateri();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getMateri() {
        dialog.show();
        materi = new ArrayList<>();
        AndroidNetworking.post(ApiServer.site_url+"get_materi.php")
                .addBodyParameter("kelas", prefManager.getLvl())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONArray d = response.getJSONArray("data");
                                Gson gson = new Gson();
                                materi.clear();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelMateri materis = gson.fromJson(data + "", ModelMateri.class);
                                    materi.add(materis);
                                }
                                AdapterMateri adapterMateri = new AdapterMateri(materi);
                                binding.rvJadwal.setAdapter(adapterMateri);
                                adapterMateri.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                    }
                });
    }
}
