package com.example.siswaal_azhar.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.siswaal_azhar.databinding.ActivityKirimTugasBinding;
import com.example.siswaal_azhar.util.ApiServer;
import com.example.siswaal_azhar.util.PrefManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class KirimTugasActivity extends AppCompatActivity {
    AlertDialog dialog;
    private ActivityKirimTugasBinding binding;
    PrefManager prefManager;
    String id_tugas;
    private String fileName;


    private static final String IMAGE_DIRECTORY = "/pdf";
    private String url = "https://www.google.com";
    private static final int BUFFER_SIZE = 1024 * 2;
    String path;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKirimTugasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        dialog = new SpotsDialog.Builder().setContext(KirimTugasActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();
        requestMultiplePermissions();

        final Intent intent = new Intent(getIntent());
        id_tugas = intent.getStringExtra("id");
        binding.judulTugas.setText(intent.getStringExtra("judul"));
        binding.deadLine.setText("Jatuh Tempo : "+intent.getStringExtra("deadline"));
        binding.isiTugas.setText(intent.getStringExtra("isi"));
        if (intent.getStringExtra("file")==null){
            binding.filePlace.setVisibility(View.GONE);
        }else {
            binding.txtNamaFile.setText(intent.getStringExtra("file"));
            binding.filePlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(KirimTugasActivity.this, PdfActivity.class);
                    intent1.putExtra("nama", intent.getStringExtra("judul"));
                    intent1.putExtra("PDF", intent.getStringExtra("file"));
                    intent1.putExtra("URL", ApiServer.pdf_tugas);
                    startActivity(intent1);
                }
            });
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.uploadFile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
            }
        });

        binding.uploadTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPDF(path);
                Log.d("io niha", "ko :"+path);
            }
        });

    }

    private void uploadPDF(final String path) {
        dialog.show();
        final File file = new File(path);
        AndroidNetworking.upload(ApiServer.site_url+"upload_pdf.php")
                .addMultipartFile("filename", file)
                .addMultipartParameter("filename", "value")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int cut = String.valueOf(file).lastIndexOf('/');
                            String sample = path.substring(cut+1);
                            Log.d("file io", "fil :"+sample);
                            simpanData(sample);

                            dialog.hide();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            jsonObject.toString().replace("\\\\", "");

                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                JSONArray dataArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    url = dataobj.optString("pathToFile");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.d("io eror", "code :"+anError);
                    }
                });
    }

    private void simpanData(String sample) {
        dialog.show();
        AndroidNetworking.post(ApiServer.site_url+"set_tugas.php")
                .addBodyParameter("ids", prefManager.getIdUser())
                .addBodyParameter("idt", id_tugas)
                .addBodyParameter("ket", binding.ketTugas.getText().toString())
                .addBodyParameter("file", sample)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog.hide();
                            if (response.getString("code").equalsIgnoreCase("1")){
                                Toasty.success(getApplicationContext(), "sukses", Toasty.LENGTH_SHORT, true).show();
                                finish();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);

            path = getFilePathFromURI(KirimTugasActivity.this,uri);
            Log.d("ioooo", myFile.getName()+"| "+uri.getPath()+"|"+uri);
            int cut = path.lastIndexOf('/');

            binding.txtFileName.setText(path.substring(cut + 1));


        }

        super.onActivityResult(requestCode, resultCode, data);

    }


    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        Log.d("ioo", "nah :"+fileName);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        Log.d("ioo", "nih :"+wallpaperDirectory);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        String fileName = null;
        if (cut != -1) {
            String sample = path.substring(cut+1);
            String last = sample.substring(sample.length()-3);
            Log.d("io:o", "ini :"+last);
            if (last.equalsIgnoreCase("pdf")) {
                fileName = path.substring(cut + 1);
            }else {
                fileName = path.substring(cut + 1)+".pdf";
            }
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
