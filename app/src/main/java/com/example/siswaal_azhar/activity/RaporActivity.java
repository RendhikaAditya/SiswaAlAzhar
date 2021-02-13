package com.example.siswaal_azhar.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.adapter.AdapterRapor;
import com.example.siswaal_azhar.databinding.ActivityRaporBinding;
import com.example.siswaal_azhar.model.ModelRapor;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RaporActivity extends AppCompatActivity {
private ActivityRaporBinding binding;
    PrefManager prefManager;
    AlertDialog dialog;
    List<ModelRapor> rapor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaporBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        dialog = new SpotsDialog.Builder().setContext(RaporActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();

        getRapor();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getRapor() {
        dialog.show();
        rapor = new ArrayList<>();
        AndroidNetworking.post(ApiServer.site_url+"get_lapor.php")
                .addBodyParameter("id", prefManager.getIdUser())
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
                                rapor.clear();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);
                                    ModelRapor rapot = gson.fromJson(data + "", ModelRapor.class);
                                    rapor.add(rapot);
                                }
                                AdapterRapor adapterMateri = new AdapterRapor(rapor);
                                binding.rvRapor.setAdapter(adapterMateri);
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