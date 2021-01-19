package com.example.siswaal_azhar.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.databinding.ActivityRegisterBinding;
import com.example.siswaal_azhar.util.ApiServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    String gender;

    final Calendar c = Calendar.getInstance();
    static final int DATE_DIALOG_ID = 999;
    private int year;
    private int month;
    private int day;

    AlertDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        dialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();

        binding.masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDaftar();
            }
        });


        String now= LocalDate.now().toString();
        binding.tglLahir.setText(now);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        binding.getTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        getGender();
    }

    private void setDaftar() {
        dialog.show();
        AndroidNetworking.post(ApiServer.site_url+"register.php")
                .addBodyParameter("nama", binding.nama.getText().toString())
                .addBodyParameter("alamat", binding.alamat.getText().toString())
                .addBodyParameter("tempat", binding.tempatLahir.getText().toString())
                .addBodyParameter("tgl", binding.tglLahir.getText().toString())
                .addBodyParameter("gender", gender)
                .addBodyParameter("nohp", binding.nohp.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog.hide();
                            if (response.getString("code").equalsIgnoreCase("1")){
                                Toasty.success(getApplicationContext(),"Berhasil Terdaftar, Tunggu Konfirmasi Admin untuk masuk",Toasty.LENGTH_LONG, true).show();
                                finish();
                            }else {
                                Toasty.error(getApplicationContext(),"Gagal Daftar 1", Toasty.LENGTH_SHORT, true).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.d("sukses", "kodde :"+anError);
                        Toasty.error(getApplicationContext(),"Gagal Daftar", Toasty.LENGTH_SHORT, true).show();
                    }
                });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener datePickerListener =
            new    DatePickerDialog.   OnDateSetListener(){

                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    year = selectedYear;
                    month = selectedMonth;
                    day = selectedDay;

                    StringBuilder date = new StringBuilder().append(year).append("-")
                            .append(month + 1).append("-").append(day).append(" ");
                    // set selected date into edittext
                    binding.tglLahir.setText(new StringBuilder().append(year).append("-")
                            .append(month + 1).append("-").append(day).append(" "));
//                    String tgl = new StringBuilder().append(year).append("-")
//                            .append(month + 1).append("-").append(day).append(" ");
                }
            };



    private void getGender() {

        binding.btLaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btLaki.setBackgroundResource(R.drawable.edmain);
                binding.txtLakiLaki.setTextColor(getResources().getColor(R.color.light));

                binding.btPerempuan.setBackgroundResource(R.drawable.edview);
                binding.txtPerempuan.setTextColor(getResources().getColor(R.color.main));

                gender = "Laki - Laki";
            }
        });

        binding.btPerempuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btPerempuan.setBackgroundResource(R.drawable.edmain);
                binding.txtPerempuan.setTextColor(getResources().getColor(R.color.light));

                binding.btLaki.setBackgroundResource(R.drawable.edview);
                binding.txtLakiLaki.setTextColor(getResources().getColor(R.color.main));

                gender = "Perempuan";
            }
        });

    }

}
