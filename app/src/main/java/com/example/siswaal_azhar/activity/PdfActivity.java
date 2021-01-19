package com.example.siswaal_azhar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.databinding.ActivityPdfBinding;
import com.example.siswaal_azhar.util.ApiServer;

import dmax.dialog.SpotsDialog;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class PdfActivity extends AppCompatActivity implements DownloadFile.Listener {
    private ActivityPdfBinding binding;

    PDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new SpotsDialog.Builder().setContext(PdfActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();

        Intent intent = new Intent(getIntent());
        String pdf = ApiServer.pdf+intent.getStringExtra("PDF");
        String nama = intent.getStringExtra("nama");
        Log.d("pdf", "| isi : "+pdf);


        binding.namaMateri.setText(nama);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        remotePDFViewPager = new RemotePDFViewPager(this, pdf, this);
        dialog.show();
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        dialog.hide();
        Log.d("pdf", "| masuk sukses : "+url);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        binding.pdfPlace.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onFailure(Exception e) {
        dialog.hide();
        Log.d("pdf", "| masuk fail : "+e);
        Toast.makeText(getApplicationContext(), "PDF tidak bisa ditampilkan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        dialog.show();
        Log.d("pdf", "| masuk update : "+total);
    }
}