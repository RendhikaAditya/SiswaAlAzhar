package com.example.siswaal_azhar.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.adapter.AdapterJadwal;
import com.example.siswaal_azhar.databinding.ActivityJadwalPelajaranBinding;
import com.example.siswaal_azhar.model.ModelJadwal;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class JadwalPelajaranActivity extends AppCompatActivity {
    private ActivityJadwalPelajaranBinding binding;
    RecyclerView recyclerView;
    List<ModelJadwal> jadwals;



    ImageView back;
    AlertDialog alertDialog;

    PrefManager prefManager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJadwalPelajaranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();

        recyclerView = binding.rvJadwal;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getHari();
        String day = "Senin";
        getJadwal(day);


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getHari() {
        binding.btSenin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btSenin.setBackgroundResource(R.drawable.edmain);
                binding.txtSenin.setTextColor(getResources().getColor(R.color.putih));

                binding.btSelasa.setBackgroundResource(R.drawable.edview);
                binding.txtSelasa.setTextColor(getResources().getColor(R.color.main));
                binding.btRabu.setBackgroundResource(R.drawable.edview);
                binding.txtRabu.setTextColor(getResources().getColor(R.color.main));
                binding.btKamis.setBackgroundResource(R.drawable.edview);
                binding.txtKamis.setTextColor(getResources().getColor(R.color.main));
                binding.btJumat.setBackgroundResource(R.drawable.edview);
                binding.txtJumat.setTextColor(getResources().getColor(R.color.main));
                binding.btSabtu.setBackgroundResource(R.drawable.edview);
                binding.txtSabtu.setTextColor(getResources().getColor(R.color.main));

                String day = "Senin";
                getJadwal(day);
            }
        });

        binding.btSelasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btSelasa.setBackgroundResource(R.drawable.edmain);
                binding.txtSelasa.setTextColor(getResources().getColor(R.color.putih));

                binding.btSenin.setBackgroundResource(R.drawable.edview);
                binding.txtSenin.setTextColor(getResources().getColor(R.color.main));
                binding.btRabu.setBackgroundResource(R.drawable.edview);
                binding.txtRabu.setTextColor(getResources().getColor(R.color.main));
                binding.btKamis.setBackgroundResource(R.drawable.edview);
                binding.txtKamis.setTextColor(getResources().getColor(R.color.main));
                binding.btJumat.setBackgroundResource(R.drawable.edview);
                binding.txtJumat.setTextColor(getResources().getColor(R.color.main));
                binding.btSabtu.setBackgroundResource(R.drawable.edview);
                binding.txtSabtu.setTextColor(getResources().getColor(R.color.main));

                String day = "Selasa";
                getJadwal(day);
            }
        });

        binding.btRabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btRabu.setBackgroundResource(R.drawable.edmain);
                binding.txtRabu.setTextColor(getResources().getColor(R.color.putih));

                binding.btSelasa.setBackgroundResource(R.drawable.edview);
                binding.txtSelasa.setTextColor(getResources().getColor(R.color.main));
                binding.btSenin.setBackgroundResource(R.drawable.edview);
                binding.txtSenin.setTextColor(getResources().getColor(R.color.main));
                binding.btKamis.setBackgroundResource(R.drawable.edview);
                binding.txtKamis.setTextColor(getResources().getColor(R.color.main));
                binding.btJumat.setBackgroundResource(R.drawable.edview);
                binding.txtJumat.setTextColor(getResources().getColor(R.color.main));
                binding.btSabtu.setBackgroundResource(R.drawable.edview);
                binding.txtSabtu.setTextColor(getResources().getColor(R.color.main));

                String day = "Rabu";
                getJadwal(day);
            }
        });

        binding.btKamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btKamis.setBackgroundResource(R.drawable.edmain);
                binding.txtKamis.setTextColor(getResources().getColor(R.color.putih));

                binding.btSelasa.setBackgroundResource(R.drawable.edview);
                binding.txtSelasa.setTextColor(getResources().getColor(R.color.main));
                binding.btRabu.setBackgroundResource(R.drawable.edview);
                binding.txtRabu.setTextColor(getResources().getColor(R.color.main));
                binding.btSenin.setBackgroundResource(R.drawable.edview);
                binding.txtSenin.setTextColor(getResources().getColor(R.color.main));
                binding.btJumat.setBackgroundResource(R.drawable.edview);
                binding.txtJumat.setTextColor(getResources().getColor(R.color.main));
                binding.btSabtu.setBackgroundResource(R.drawable.edview);
                binding.txtSabtu.setTextColor(getResources().getColor(R.color.main));

                String day = "Kamis";
                getJadwal(day);
            }
        });

        binding.btJumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btJumat.setBackgroundResource(R.drawable.edmain);
                binding.txtJumat.setTextColor(getResources().getColor(R.color.putih));

                binding.btSelasa.setBackgroundResource(R.drawable.edview);
                binding.txtSelasa.setTextColor(getResources().getColor(R.color.main));
                binding.btRabu.setBackgroundResource(R.drawable.edview);
                binding.txtRabu.setTextColor(getResources().getColor(R.color.main));
                binding.btKamis.setBackgroundResource(R.drawable.edview);
                binding.txtKamis.setTextColor(getResources().getColor(R.color.main));
                binding.btSenin.setBackgroundResource(R.drawable.edview);
                binding.txtSenin.setTextColor(getResources().getColor(R.color.main));
                binding.btSabtu.setBackgroundResource(R.drawable.edview);
                binding.txtSabtu.setTextColor(getResources().getColor(R.color.main));

                String day = "Jumat";
                getJadwal(day);
            }
        });

        binding.btSabtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btSabtu.setBackgroundResource(R.drawable.edmain);
                binding.txtSabtu.setTextColor(getResources().getColor(R.color.putih));

                binding.btSelasa.setBackgroundResource(R.drawable.edview);
                binding.txtSelasa.setTextColor(getResources().getColor(R.color.main));
                binding.btRabu.setBackgroundResource(R.drawable.edview);
                binding.txtRabu.setTextColor(getResources().getColor(R.color.main));
                binding.btKamis.setBackgroundResource(R.drawable.edview);
                binding.txtKamis.setTextColor(getResources().getColor(R.color.main));
                binding.btJumat.setBackgroundResource(R.drawable.edview);
                binding.txtJumat.setTextColor(getResources().getColor(R.color.main));
                binding.btSenin.setBackgroundResource(R.drawable.edview);
                binding.txtSenin.setTextColor(getResources().getColor(R.color.main));

                String day = "Sabtu";
                getJadwal(day);
            }
        });
    }


    private void getJadwal(String day) {
        alertDialog.show();
        jadwals = new ArrayList<>();
        binding.kosong.setVisibility(View.VISIBLE);
        AndroidNetworking.post(ApiServer.site_url+"get_jadwal_pelajaran.php")
                .addBodyParameter("hari", day)
                .addBodyParameter("kelas", prefManager.getLvl())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            Log.d("sukses", "code"+response);
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                recyclerView.setVisibility(View.VISIBLE);
                                binding.kosong.setVisibility(View.GONE);
                                JSONArray d = response.getJSONArray("data");
                                Gson gson = new Gson();
                                jadwals.clear();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelJadwal jadwal = gson.fromJson(data + "", ModelJadwal.class);
                                    jadwals.add(jadwal);
                                }
                                AdapterJadwal adapterJadwal = new AdapterJadwal(jadwals);
                                recyclerView.setAdapter(adapterJadwal);
                                adapterJadwal.notifyDataSetChanged();
                            }else {
                                recyclerView.setVisibility(View.GONE);
                                binding.kosong.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror", "code :"+anError);
                    }
                });
    }
}
