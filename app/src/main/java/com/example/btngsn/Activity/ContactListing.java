package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.btngsn.R;

public class ContactListing extends AppCompatActivity {
    String titile, idForm, idSpecies, acreage, price, address, description, floors,
            bedroom, toilet, idHouse, idBancoly, furniture, juridical, nameContact, phoneContact, idUser, dateStart, dateEnd;
    byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_listing);
        //MyPackage
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("MyPackage");
        if (bundle != null) {
            titile = bundle.getString("tieude", "");
            Toast.makeText(this, ""+titile, Toast.LENGTH_SHORT).show();
            acreage = bundle.getString("dientich", "");
            address = bundle.getString("diachi", "");
            description = bundle.getString("mota", "");
            furniture = bundle.getString("noithat", "");
            juridical = bundle.getString("phaply", "");
            floors = bundle.getString("sotang", "");
            bedroom = bundle.getString("sophong", "");
            toilet = bundle.getString("sotoilet", "");
            image = bundle.getByteArray("hinhanh");
        }

    }

}