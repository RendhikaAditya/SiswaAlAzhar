package com.example.siswaal_azhar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.adapter.AdapterMateri;
import com.example.siswaal_azhar.adapter.AdapterQuis;
import com.example.siswaal_azhar.model.ModelQuis;
import com.example.siswaal_azhar.util.ApiServer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuisActivity extends AppCompatActivity {


    private List<ModelQuis> dataQUis;
    private RecyclerView recycler_quis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quis);

        AndroidNetworking.initialize(this);

        recycler_quis = findViewById(R.id.recycler_quis);
        recycler_quis.setHasFixedSize(true);
        recycler_quis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dataQUis = new ArrayList<>();
        getPelajaran();
    }
    public void getPelajaran(){

        AndroidNetworking.get(ApiServer.site_url+"get_pelajaran.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            Log.d("tampil barang","response:"+response);
                              JSONArray res = response.getJSONArray("data");
                                Gson gson = new Gson();
                                dataQUis.clear();
                                for (int i=0; i<res.length(); i++){
                                    JSONObject data = res.getJSONObject(i);
                                    ModelQuis Isi = gson.fromJson(data + "", ModelQuis.class);
                                    dataQUis.add(Isi);
                                }

                            AdapterQuis adapterQuis = new AdapterQuis(dataQUis);
                            recycler_quis.setAdapter(adapterQuis);
                            adapterQuis.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }

}