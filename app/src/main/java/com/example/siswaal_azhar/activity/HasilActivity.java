package com.example.siswaal_azhar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class HasilActivity extends AppCompatActivity {

    TextView mtvHasilAkhir;
    Button mbtnMenu, btnReplay;
    String id_pelajaran, skorPilGan;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        prefManager = new PrefManager(this);

        Intent i = getIntent();
        id_pelajaran = i.getStringExtra("id_pelajaran");

        btnReplay = findViewById(R.id.btnReplay);
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(HasilActivity.this, SoalQuisActivity.class);
                j.putExtra("id_pelajaran",id_pelajaran);

                /* Toast.makeText(HasilSkoring.this, "id"+id, Toast.LENGTH_SHORT).show();*/

                startActivity(j);
            }
        });

        mtvHasilAkhir = (TextView) findViewById(R.id.tvSkorAkhir);
        mbtnMenu = (Button) findViewById(R.id.btnMenu);

        setSkor();

        mbtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HasilActivity.this, QuisActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


    }

    //method untuk mengatur skor yang akan ditampilkan pada textview
    public void setSkor(){
        //hasil lemparan (putExtra) dari activity sebelumnya ditampung dalam variabel lokal
        String activity = getIntent().getStringExtra("activity");
        skorPilGan = getIntent().getStringExtra("skorAkhir");


        if(activity.equals("PilihanGanda")){ //jika var activity bernilai PilihanGanda
            //dipastikan activity sebelumnya datang dari kelas KuisPilihanGanda
            //maka skornya diatur dari skorPilGan

            mtvHasilAkhir.setText("Skor : "+skorPilGan );
            inputNilai();
        }
    }

    private void inputNilai() {

        AndroidNetworking.post(ApiServer.site_url+"set_nilai.php")
                .addBodyParameter("id_siswa", prefManager.getIdUser())
                .addBodyParameter("id_pelajaran", id_pelajaran)
                .addBodyParameter("nilai", skorPilGan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int stat = response.getInt("status");
                            String message = response.getString("message");
                            Log.d("sukses", "code"+response);
                            if (stat == 1){

                                Toast.makeText(HasilActivity.this, "Nilai Tersimpan", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(HasilActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code :"+anError);
                        Toast.makeText(HasilActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onBackPressed(){
        Toast.makeText(this, "Tekan Tombol Home ", Toast.LENGTH_SHORT).show();
        //jadi yang awalnya klik tombol back maka akan kembali ke activity sebelumnya
        //kali ini ketika tombol back diklik maka
        //hanya muncul Toast
    }

}
