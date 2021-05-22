package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetail extends AppCompatActivity {
    public Toolbar toolbar;
    ImageView imageView;
    FloatingActionButton fab;
    TextView titileName, price, inforDescription, Acreage, address, Species, directionHouse, NumberBed, NumberToilet, DatePost, phaply;
    TextView nameUser, phoneUser, emailUser;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        init();
        ActionTool();
        GetInfor();
        Event();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        titileName = (TextView) findViewById(R.id.titileName);
        price = (TextView) findViewById(R.id.price);
        inforDescription = (TextView) findViewById(R.id.inforDescription);
        Acreage = (TextView) findViewById(R.id.Acreage);
        address = (TextView) findViewById(R.id.address);
        Species = (TextView) findViewById(R.id.Species);
        directionHouse = (TextView) findViewById(R.id.directionHouse);
        NumberBed = (TextView) findViewById(R.id.NumberBed);
        NumberToilet = (TextView) findViewById(R.id.NumberToilet);
        DatePost = (TextView) findViewById(R.id.DatePost);
        phaply = (TextView) findViewById(R.id.phaply);
        nameUser = (TextView) findViewById(R.id.nameUser);
        phoneUser = (TextView) findViewById(R.id.phoneUser);
        emailUser = (TextView) findViewById(R.id.emailUser);

        imageView = (ImageView) findViewById(R.id.imageView);
        fab = (FloatingActionButton) findViewById(R.id.fab);


    }

    public void GetInfor() {
        Intent intent = (Intent) getIntent();
        Listing listing = (Listing) intent.getParcelableExtra("thongtinchitiet");

        titileName.setText(listing.getTitle());
        price.setText(listing.getPrice());

        inforDescription.setText(listing.getDescription());
        Acreage.setText(listing.getAcreage() + " M2 ");
        address.setText(listing.getAddress());
        Species.setText(listing.getIdSpecies());
        directionHouse.setText(listing.getDirectionHouse());
        NumberBed.setText(listing.getBedroom());
        NumberToilet.setText(listing.getToilet());
        DatePost.setText(listing.getDateStart());
        phaply.setText(listing.getJuridical());
        nameUser.setText(listing.getNameContact());
        phoneUser.setText("0"+listing.getPhoneContact());
        emailUser.setText(listing.getEmailContact());

        titileName.setMaxLines(3);
        titileName.setEllipsize(TextUtils.TruncateAt.END);

        Glide.with(getApplicationContext()).load(listing.getImage()).placeholder(R.drawable.ic_baseline_hide_image_24).error(R.drawable.ic_baseline_error_24).centerCrop().into(imageView);
    }

    private void ActionTool() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24) );
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        callIntent.setData(Uri.parse("tel:" + phoneUser.getText().toString()));
        try {
            this.startActivity(callIntent);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Your call failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    public void Event(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDailog();
            }
        });
    }
    public void ShowDailog(){
        Dialog callDialog = new Dialog(ProductDetail.this);
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