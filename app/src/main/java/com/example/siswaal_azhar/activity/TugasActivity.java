package com.example.siswaal_azhar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.adapter.AdapterMateri;
import com.example.siswaal_azhar.adapter.AdapterTugas;
import com.example.siswaal_azhar.databinding.ActivityTugasBinding;
import com.example.siswaal_azhar.model.ModelMateri;
import com.example.siswaal_azhar.model.ModelTugas;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class TugasActivity extends AppCompatActivity {
    private ActivityTugasBinding binding;
    PrefManager prefManager;
    AlertDialog dialog;
    List<ModelTugas> tugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTugasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        dialog = new SpotsDialog.Builder().setContext(TugasActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getTugas();
    }

    private void getTugas() {
        dialog.show();
        tugas = new ArrayList<>();
        AndroidNetworking.post(ApiServer.site_url+"get_tugas.php")
                .addBodyParameter("kelas", prefManager.getLvl())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("sukses", "suv:"+response);
                            dialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONArray d = response.getJSONArray("data");
                                Gson gson = new Gson();
                                tugas.clear();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelTugas tugass = gson.fromJson(data + "", ModelTugas.class);
                                    tugas.add(tugass);
                                }
                                AdapterTugas adapterTugas = new AdapterTugas(tugas);
                                binding.rvTugas.setAdapter(adapterTugas);
                                adapterTugas.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.d("eor", "gagl Tugas :"+anError);
                    }
                });
    }
}
