package com.example.siswaal_azhar.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
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
import com.example.siswaal_azhar.databinding.ActivitySppBinding;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class SppActivity extends AppCompatActivity {
private ActivitySppBinding binding;
    static final int DATE_DIALOG_ID = 1;
    private int mYear = 2013;
    private int mMonth = 5;
    private int mDay = 30;
    private DatePickerDialog datePickerDialog;
    int month, yer;
    AlertDialog alertDialog;
    PrefManager prefManager;
    Bitmap bitmap, decoded;

    int success =0 ;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();
        prefManager = new PrefManager(this);
        AndroidNetworking.initialize(this);
        binding.sudahBayar.setVisibility(View.GONE);
        binding.bayar.setVisibility(View.GONE);

        binding.getSmester.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

               final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        month = monthOfYear + 1;
                        yer = year;
                        binding.smesterTxt.setText( "Bulan : " + formatMonth((month)+"") + " Tahun : " + year);
                        cekSpp();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        binding.getSmester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        binding.getImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        binding.kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });
    }

    private void kirim() {
        alertDialog.show();
        AndroidNetworking.post(ApiServer.site_url+"set_spp.php")
                .addBodyParameter("id", prefManager.getIdUser())
                .addBodyParameter("bulan", month+"")
                .addBodyParameter("tahun",yer+"")
                .addBodyParameter("foto", getStringImage(decoded))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("sukses", "code : "+response);
                            if (response.getString("code").equalsIgnoreCase("1")){
                                Toasty.success(SppActivity.this,"sukses", Toasty.LENGTH_SHORT, true).show();
                                finish();
                            }else {
                                Toasty.error(SppActivity.this,"Gagal", Toasty.LENGTH_SHORT, true).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toasty.error(SppActivity.this,"Gagal", Toasty.LENGTH_SHORT, true).show();
                        Log.d("eror", "code : "+anError);
                    }
                });
    }

    private void cekSpp() {
        alertDialog.show();
        binding.sudahBayar.setVisibility(View.GONE);
        binding.bayar.setVisibility(View.GONE);
        AndroidNetworking.post(ApiServer.site_url+"get_spp.php")
                .addBodyParameter("id", prefManager.getIdUser())
                .addBodyParameter("bulan", month+"")
                .addBodyParameter("tahun",yer+"")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("sukses", "code : "+response);
                            Log.d("data", "code : "+prefManager.getIdUser()+"  | "+month+"  | "+yer);
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                binding.sudahBayar.setVisibility(View.VISIBLE);
                                binding.bayar.setVisibility(View.GONE);
                                binding.kirim.setVisibility(View.GONE);
                            }else {
                                binding.sudahBayar.setVisibility(View.GONE);
                                binding.bayar.setVisibility(View.VISIBLE);
                                binding.kirim.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror", "code : "+anError);
                    }
                });


    }

    public String formatMonth(String month) {
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");
        try {
            return monthDisplay.format(monthParse.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
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

}