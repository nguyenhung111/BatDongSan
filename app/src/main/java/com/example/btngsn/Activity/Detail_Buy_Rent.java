package com.example.btngsn.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.btngsn.Model.BuyRent;
import com.example.btngsn.Model.Image;
import com.example.btngsn.Model.Listing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.R;

public class Detail_Buy_Rent extends AppCompatActivity {

    TextView tieude, mota, gia, dientich, diachi, loaitin, ngaydangtin, ngayketthuc, tenlienlac, dienthoai, DClienlac, Email;
    ImageView imageView;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__buy__rent);

        init();
        getData();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDailog();
            }
        });
    }

    public void init() {
        tieude = (TextView) findViewById(R.id.tieude);
        mota = (TextView) findViewById(R.id.txtmota);
        gia = (TextView) findViewById(R.id.txtGia);
        dientich = (TextView) findViewById(R.id.txtDientich);
        diachi = (TextView) findViewById(R.id.txtDiachi);
        loaitin = (TextView) findViewById(R.id.txtLoaitin);
        ngaydangtin = (TextView) findViewById(R.id.txtNgaydang);
        ngayketthuc = (TextView) findViewById(R.id.txtNgayketthuc);
        tenlienlac = (TextView) findViewById(R.id.txtTen);
        dienthoai = (TextView) findViewById(R.id.txtMobile);
        DClienlac = (TextView) findViewById(R.id.txtDiachiLienhe);
        Email = (TextView) findViewById(R.id.txtEmail);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void getData() {
        Intent intent = (Intent) getIntent();
        BuyRent buyRent = (BuyRent) intent.getParcelableExtra("thongtinchitiet");

        tieude.setText(buyRent.getTieude());
        mota.setText(buyRent.getNoidung());
        gia.setText(buyRent.getGia());
        dientich.setText(buyRent.getDientich());
        diachi.setText(buyRent.getDiachi());
        loaitin.setText(buyRent.getLoaidat());
        ngaydangtin.setText(buyRent.getDateStart());
        ngayketthuc.setText(buyRent.getDateEnd());
        tenlienlac.setText(buyRent.getTenlienhe());
        dienthoai.setText("0"+buyRent.getDienthoai());
        DClienlac.setText(buyRent.getDiachilienhe());
        Email.setText(buyRent.getEmail());
        String url = buyRent.getHinhanh();
        Glide.with(this).load(url).centerCrop().placeholder(R.drawable.img_error).error(R.drawable.ic_baseline_hide_image_24).into(imageView);

    }
    private void askPermissionAndCall() {

        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have Call permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSION_REQUEST_CODE_CALL_PHONE
                );
                return;
            }
        }
        this.callNow();
    }

    @SuppressLint("MissingPermission")
    private void callNow() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + dienthoai.getText().toString()));
        try {
            this.startActivity(callIntent);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Your call failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    public void ShowDailog() {
        Dialog callDialog = new Dialog(Detail_Buy_Rent.this);
        callDialog.setContentView(R.layout.custom_layout_call);
        callDialog.setCancelable(true);
        callDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button callDialog_btn = callDialog.findViewById(R.id.yes);
        Button exitDialog_btn = callDialog.findViewById(R.id.no);
        callDialog_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
                askPermissionAndCall();
            }
        });

        exitDialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
            }
        });
        callDialog.show();
    }


}