package com.example.btngsn.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UpdateListing extends AppCompatActivity {
    TextView titileName, price, inforDescription, Acreage, address, Species, directionHouse, NumberBed, NumberToilet, DatePost, phaply, dateEnd;
    TextView nameUser, phoneUser, emailUser, noithat, numberFloor, huongbancong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatetin);

        init();
        GetInfor();
    }

    public void init() {

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
        dateEnd = (TextView) findViewById(R.id.dateEnd);
        phaply = (TextView) findViewById(R.id.phaply);
        nameUser = (TextView) findViewById(R.id.nameUser);
        phoneUser = (TextView) findViewById(R.id.phoneUser);
        emailUser = (TextView) findViewById(R.id.emailUser);
        noithat = (TextView) findViewById(R.id.noithat);
        numberFloor = (TextView) findViewById(R.id.numberFloor);
        huongbancong = (TextView) findViewById(R.id.huongbancong);

    }

    public void GetInfor() {
        Intent intent = (Intent) getIntent();
        Listing listing = (Listing) intent.getParcelableExtra("thongtinchitiet");

        String idListing = listing.getIdListing();
        titileName.setText(listing.getTitle());
        price.setText(listing.getPrice() + " " + listing.getUnit());
        inforDescription.setText(listing.getDescription());
        Acreage.setText(listing.getAcreage() + " M2 ");
        address.setText(listing.getAddressDetail());
        Species.setText(listing.getIdSpecies());
        directionHouse.setText(listing.getDirectionHouse());
        huongbancong.setText(listing.getDirectionBancoly());
        String sotang = listing.getFloors();
        numberFloor.setText(listing.getFloors());
        NumberBed.setText(listing.getBedroom());
        NumberToilet.setText(listing.getToilet());
        noithat.setText(listing.getFurniture());
        DatePost.setText(listing.getDateStart());
        dateEnd.setText(listing.getDateEnd());

        phaply.setText(listing.getJuridical());

        nameUser.setText(listing.getNameContact());
        phoneUser.setText("0" + listing.getPhoneContact());
        emailUser.setText(listing.getEmailContact());


        titileName.setMaxLines(3);
        titileName.setEllipsize(TextUtils.TruncateAt.END);
    }

}
