package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.btngsn.Adapter.formAdapter;
import com.example.btngsn.Adapter.viewPaperImage;
import com.example.btngsn.Model.Favorite;
import com.example.btngsn.Model.Image;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.viewForm;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends AppCompatActivity {
    public Toolbar toolbar;
    ImageView imageView, imageViewFavorite;
    FloatingActionButton fab;
    TextView titileName, price, inforDescription, Acreage, address, Species, directionHouse, NumberBed, NumberToilet, DatePost, phaply;
    TextView nameUser, phoneUser, emailUser;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    String idListing = "";
    String idUser = "";
    ArrayList<Favorite> arrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ViewPager viewPager;
    private ArrayList<Image> list;
    private viewPaperImage adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        init();
        initShare();
        GetInfor();
        getFavortie();
        Event();
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
        phaply = (TextView) findViewById(R.id.phaply);
        nameUser = (TextView) findViewById(R.id.nameUser);
        phoneUser = (TextView) findViewById(R.id.phoneUser);
        emailUser = (TextView) findViewById(R.id.emailUser);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(viewListener);

        imageViewFavorite = (ImageView) findViewById(R.id.imageViewFavorite);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void GetInfor() {
        Intent intent = (Intent) getIntent();
        Listing listing = (Listing) intent.getParcelableExtra("thongtinchitiet");

        idListing = listing.getIdListing();
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
        phoneUser.setText("0" + listing.getPhoneContact());
        emailUser.setText(listing.getEmailContact());

        titileName.setMaxLines(3);
        titileName.setEllipsize(TextUtils.TruncateAt.END);

        DataClient getData = APIUtils.getData();
        Call<List<Image>> callback = getData.getImage(listing.getIdListing());
        callback.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                list =  (ArrayList<Image>) response.body();
                Log.d("list", String.valueOf(list.size()));
                if(list.size() > 0 ){
                    adapter = new viewPaperImage(list,ProductDetail.this );
                    viewPager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Log.d("mes", t.getMessage());
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
            Toast.makeText(getApplicationContext(), "Your call failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void Event() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDailog();
            }
        });

    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void ShowDailog() {
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

    public void getFavortie() {
        idUser = sharedPreferences.getString("idUser", "");
        arrayList = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<Favorite>> callback = getData.getFavorite(idUser, idListing);
        callback.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                arrayList = (ArrayList<Favorite>) response.body();
                if (arrayList.size() > 0) {
                    imageViewFavorite.setImageResource(R.drawable.favorite_red);
                    delete();
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Fravorit();
            }
        });
    }

    public void Fravorit() {
        idUser = sharedPreferences.getString("idUser", "");
        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(idUser)) {
                    DataClient dataClient = APIUtils.getData();
                    retrofit2.Call<String> call = dataClient.PostFavorit(idUser, idListing);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if (result.equals("1")) {
                                Toast.makeText(ProductDetail.this, "Đã yêu thích", Toast.LENGTH_SHORT).show();
                                getFavortie();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(ProductDetail.this, "Đăng nhập để sử dụng chức năng này", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void delete() {
        idUser = sharedPreferences.getString("idUser", "");
        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient deleteListing = APIUtils.getData();
                retrofit2.Call<String> callback = deleteListing.deleteFavorite(idListing, idUser);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null) {
                            int result = Integer.parseInt(response.body());
                            if (result == 1) {
                                imageViewFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                                Toast.makeText(getApplicationContext(), "Bỏ yêu thích", Toast.LENGTH_SHORT).show();
                                getFavortie();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("loi", t.getMessage());
                    }
                });
            }
        });
    }
}