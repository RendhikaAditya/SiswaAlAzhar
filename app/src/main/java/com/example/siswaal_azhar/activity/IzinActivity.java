package com.example.siswaal_azhar.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.databinding.ActivityIzinBinding;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class IzinActivity extends AppCompatActivity {
    private ActivityIzinBinding binding;

    final Calendar c = Calendar.getInstance();
    static final int DATE_DIALOG_ID = 999;
    private int year;
    private int month;
    private int day;

    AlertDialog dialog;

    Bitmap bitmap, decoded;

    int success =0 ;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    PrefManager prefManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIzinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);
        AndroidNetworking.initialize(this);

        dialog = new SpotsDialog.Builder().setContext(IzinActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();
        String now= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDate.now().toString();
        }
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

        binding.getImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        binding.ajukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.ketIzin.getText().toString().isEmpty()) {
                    if (success==0){
                        Toasty.error(getApplicationContext(), "Upload Foto Surat Izin", Toasty.LENGTH_SHORT, true).show();
                    }else {
                        simpanIzin();
                    }
                }else {
                    binding.ketIzin.setError("Isi keterangan");
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void simpanIzin() {
        dialog.show();
        AndroidNetworking.post(ApiServer.site_url+"set_izin.php")
                .addBodyParameter("ket", binding.ketIzin.getText().toString())
                .addBodyParameter("ids", prefManager.getIdUser())
                .addBodyParameter("tgl", binding.tglLahir.getText().toString())
                .addBodyParameter("foto", getStringImage(decoded))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog.hide();
                            if (response.getString("code").equalsIgnoreCase("1")){
                                Toasty.success(getApplicationContext(), "Izin Berhasil DiAjukan", Toasty.LENGTH_SHORT, true).show();
                                finish();
                            }else {
                                Toasty.error(getApplicationContext(), "Izin Gagal DiAjukan", Toasty.LENGTH_SHORT, true).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.d("eror", "fotoUp + "+anError+" | "+prefManager.getIdUser()+" | "+binding.tglLahir.getText().toString()+" | "+binding.ketIzin.getText().toString()+" | "+getStringImage(decoded));

                        Toasty.error(getApplicationContext(), "Koneksi Bermasalah", Toasty.LENGTH_SHORT, true).show();
                    }
                });


    }

    private void showFileChooser()  {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        binding.imgPlace.setImageBitmap(decoded);

        success = 1;
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                    StringBuilder date;
                    if (new StringBuilder().append(day).length()==1) {
                        if (new StringBuilder().append(month).length()==1){
                            date = new StringBuilder().append(year).append("-0")
                                    .append(month + 1).append("-0").append(day).append("");
                        }else {
                            date = new StringBuilder().append(year).append("-")
                                    .append(month + 1).append("-0").append(day).append("");
                        }
                    }else {
                        if (new StringBuilder().append(month).length()==1){
                            date = new StringBuilder().append(year).append("-0")
                                    .append(month + 1).append("-").append(day).append("");
                        }else {
                            date = new StringBuilder().append(year).append("-")
                                    .append(month + 1).append("-").append(day).append("");
                        }
                    }
                    // set selected date into edittext
                    binding.tglLahir.setText(date);
                }
            };



}
