package com.example.siswaal_azhar.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SoalQuisActivity extends AppCompatActivity {

    TextView mtvSkor, mtvSoal;
    RadioGroup mrgPilihanJawaban;
    RadioButton mrbPilihanJawaban1, mrbPilihanJawaban2, mrbPilihanJawaban3, mrbPilihanJawaban4, mrbPilihanJawaban5;
    Button mbtnSubmit;
    int skor = 0;
    int arr; //untuk menampung nilai panjang array
    int x;   //menunjukkan konten sekarang
    String kunci; //menampung jawaban benar

    String id_pelajaran, id_materi;


    ArrayList<String> arraySoal = new ArrayList<>();
    ArrayList<ArrayList<String>> arrayJawaban = new ArrayList<>();
    ArrayList<String> arrayKunci = new ArrayList<>();

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal_quis);
        prefManager = new PrefManager(this);
        AndroidNetworking.initialize(this);

        Intent i = getIntent();
        id_pelajaran = i.getStringExtra("id_pelajaran");
        Log.e("id_pelajaran",id_pelajaran);

        mtvSkor = (TextView) findViewById(R.id.tvSkor);
        mtvSoal = (TextView) findViewById(R.id.tvSoal);
        mrgPilihanJawaban = (RadioGroup) findViewById(R.id.rgPilihanJawaban);
        mrbPilihanJawaban1 = (RadioButton) findViewById(R.id.rbPilihanJawaban1);
        mrbPilihanJawaban2 = (RadioButton) findViewById(R.id.rbPilihanJawaban2);
        mrbPilihanJawaban3 = (RadioButton) findViewById(R.id.rbPilihanJawaban3);
        mrbPilihanJawaban4 = (RadioButton) findViewById(R.id.rbPilihanJawaban4);

        mbtnSubmit = (Button) findViewById(R.id.btnSubmit);

        //set konten
        mtvSkor.setText("" + skor);
        /*setKonten();*/

        //menentukan aksi ketika button submit diklik
        mbtnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //aksinya disini
                //aksinya adalah ketika button tersebut diklik maka
                //akan mengecek jawaban benar atau salah
                //kemudian konten akan berubah (next konten)
                cekJawaban();
            }
        });

        ambilData();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setKonten() {
        mrgPilihanJawaban.clearCheck();
        arr = arraySoal.size();
        if (x >= arr) { //jika nilai x melebihi nilai arr(panjang array) maka akan pindah activity (kuis selesai)
            //menjadikan skor menjadi string
            String jumlahSkor = String.valueOf(skor);
            Log.e("nilai", "" + jumlahSkor);
            Intent i = new Intent(SoalQuisActivity.this, HasilActivity.class);
            //waktu pindah activity, sekalian membawa nilai jumlahSkor yang ditampung dengan inisial skorAkhir
            //singkatnya skorAkhir = jumlahSkor
            i.putExtra("id_pelajaran", id_pelajaran);
            /*Toast.makeText(this, "id_materi"+id, Toast.LENGTH_SHORT).show();*/
            i.putExtra("activity", "PilihanGanda");
            i.putExtra("skorAkhir", jumlahSkor);


            startActivity(i);
        } else {
            //setting text dengan mengambil text dari method getter di kelas SoalPilihanGanda

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mtvSoal.setText(Html.fromHtml(arraySoal.get(x), Html.FROM_HTML_MODE_COMPACT));
                mrbPilihanJawaban1.setText(Html.fromHtml(arrayJawaban.get(x).get(0), Html.FROM_HTML_MODE_COMPACT));
                mrbPilihanJawaban2.setText(Html.fromHtml(arrayJawaban.get(x).get(1), Html.FROM_HTML_MODE_COMPACT));
                mrbPilihanJawaban3.setText(Html.fromHtml(arrayJawaban.get(x).get(2), Html.FROM_HTML_MODE_COMPACT));
                mrbPilihanJawaban4.setText(Html.fromHtml(arrayJawaban.get(x).get(3), Html.FROM_HTML_MODE_COMPACT));
                kunci = Html.fromHtml(arrayKunci.get(x), Html.FROM_HTML_MODE_COMPACT).toString();
            } else {
                mtvSoal.setText(Html.fromHtml(arraySoal.get(x)));
                mrbPilihanJawaban1.setText(Html.fromHtml(arrayJawaban.get(x).get(0)));
                mrbPilihanJawaban2.setText(Html.fromHtml(arrayJawaban.get(x).get(1)));
                mrbPilihanJawaban3.setText(Html.fromHtml(arrayJawaban.get(x).get(2)));
                mrbPilihanJawaban4.setText(Html.fromHtml(arrayJawaban.get(x).get(3)));
                kunci = String.valueOf(Html.fromHtml(arrayKunci.get(x)));
            }


        }
        x++;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cekJawaban() {

        if (mrbPilihanJawaban1.isChecked()) { //jika radio button 1 diklik
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (mrbPilihanJawaban1.getText().toString().equals(kunci)) {
                skor = skor + 10;
                mtvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                mtvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else if (mrbPilihanJawaban2.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (mrbPilihanJawaban2.getText().toString().equals(kunci)) {
                skor = skor + 10;
                mtvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                mtvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else if (mrbPilihanJawaban3.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (mrbPilihanJawaban3.getText().toString().equals(kunci)) {
                skor = skor + 10;
                mtvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                mtvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else if (mrbPilihanJawaban4.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (mrbPilihanJawaban4.getText().toString().equals(kunci)) {
                skor = skor + 10;
                mtvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                mtvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else {
            Toast.makeText(this, "Silahkan pilih jawaban dulu!", Toast.LENGTH_SHORT).show();
        }
    }

    //ini adalah method bawaan dari Android Studio
    //fungsi : memberi aksi ketika tombol back pada hp diklik
    public void onBackPressed() {
        Toast.makeText(this, "Selesaikan kuis", Toast.LENGTH_SHORT).show();
        //jadi yang awalnya klik tombol back maka akan kembali ke activity sebelumnya
        //kali ini ketika tombol back diklik maka
        //hanya muncul Toast
    }

    public void ambilData() {
        Log.d("soal", "code : "+id_pelajaran);
        AndroidNetworking.post(ApiServer.site_url + "get_DataSoal.php")
                .addBodyParameter("id_pelajaran", id_pelajaran)
                .addBodyParameter("id_kelas", prefManager.getLvl())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("kode").equalsIgnoreCase("1")) {
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    ArrayList<String> subJawaban = new ArrayList<>();
                                    JSONObject obj = data.getJSONObject(i);
                                    String id_quis = obj.getString("id_quis");
                                    String id_pelajaran = obj.getString("id_pelajaran");
                                    String soal = obj.getString("soal");

                                    String pil_a = obj.getString("pil_a");
                                    subJawaban.add(pil_a);

                                    String pil_b = obj.getString("pil_b");
                                    subJawaban.add(pil_b);

                                    String pil_c = obj.getString("pil_c");
                                    subJawaban.add(pil_c);

                                    String pil_d = obj.getString("pil_d");
                                    subJawaban.add(pil_d);


                                    String kunci = obj.getString("kunci");
                                    arraySoal.add(soal);
                                    arrayJawaban.add(subJawaban);
                                    arrayKunci.add(kunci);


                                }
                                setKonten();
                            } else {
                                Toast.makeText(SoalQuisActivity.this,"Gagal", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("tampil menu", "response:" + anError);
                    }
                });


    }
}