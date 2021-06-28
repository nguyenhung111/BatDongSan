package com.example.btngsn.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btngsn.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class mota_mua_thue extends AppCompatActivity {
    private EditText edttieude, edtdientich, edtmota,  edtgiatien;
    private Spinner spndonvi;
    private ImageView imageView;
    private Button countine;
    private TextView txttongtien;

    final int REQUEST_CHOOSE_PHOTO = 321;
    private final int REQUEST_CODE_PERMISSIONS = 1;
    String realpath ;
    String hinhthuc, loai, diachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mota_mua_thue);
        init();
        ChoosePhoto();
        EventSpiner();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            hinhthuc = bundle.getString("hinhthuc", "");
            loai = bundle.getString("loai", "");
            diachi = bundle.getString("diachi", "");
        }
        senddata();
    }

    public void init(){
        countine = (Button) findViewById(R.id.countine);
        edttieude = (EditText) findViewById(R.id.edttieude);
        edtdientich = (EditText) findViewById(R.id.edtdientich);
        edtgiatien = (EditText) findViewById(R.id.edtgiatien);
        edtmota = (EditText) findViewById(R.id.edtmota);
        imageView = (ImageView) findViewById(R.id.imageView);
        spndonvi = (Spinner) findViewById(R.id.spnloaitien);
        txttongtien = (TextView) findViewById(R.id.txttongtien);

    }
    private void EventSpiner() {
        String[] donvi = new String[]{"Thỏa thuân", "Triệu", "Tỷ", "Trăm nghìn/m2", "Triệu/m2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, donvi);
        spndonvi.setAdapter(arrayAdapter);
    }
    private void ChoosePhoto() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askForPermission();
                } else {
                    showChooser();
                }
            }

        });
    }

    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    realpath = getRealPathFromURI(imageUri);
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Glide.with(mota_mua_thue.this).load(imageUri).centerCrop().into(imageView);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void askForPermission() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            /* Ask for permission */
            // need to request permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please grant permissions to write data in sdcard",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        v -> ActivityCompat.requestPermissions(mota_mua_thue.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_PERMISSIONS)).show();
            } else {
                /* Request for permission */
                ActivityCompat.requestPermissions(mota_mua_thue.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            }

        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showChooser();
            } else {
                // Permission Denied
                Toast.makeText(mota_mua_thue.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();

        return path;
    }

    public void senddata() {

        countine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String giatien = edtgiatien.getText().toString();
                String donvitinh = spndonvi.getSelectedItem().toString();
                String s = giatien.concat(donvitinh);
                txttongtien.setText(s);
                Intent myIntent = new Intent(mota_mua_thue.this, lienhe_mua_thue.class);
                Bundle bundle = new Bundle();
                bundle.putString("tieude", edttieude.getText().toString().trim());
                bundle.putString("dientich", edtdientich.getText().toString().trim());
                bundle.putString("giatien", giatien);
                bundle.putString("donvitinh", donvitinh);
                bundle.putString("mota", edtmota.getText().toString().trim());
                bundle.putString("hinhthuc", hinhthuc);
                bundle.putString("loai", loai);
                bundle.putString("diachi", diachi);
                bundle.putString("hinhanh", realpath);
                //Đưa Bundle vào Intent
                myIntent.putExtras(bundle);
                //Mở Activity ResultActivity
                startActivity(myIntent);
            }
        });

    }
}