package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.btngsn.Adapter.AdapterTinTuongTu;
import com.example.btngsn.Adapter.ListingAdapter;
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
    ImageView imageViewFavorite;
    FloatingActionButton fab;
    TextView titileName, price, inforDescription, Acreage, address, Species, directionHouse, NumberBed, NumberToilet, DatePost, phaply, dateEnd;
    TextView nameUser, phoneUser, emailUser, noithat, numberFloor, huongbancong;
    private RecyclerView recyviews;
    private AdapterTinTuongTu adapterTinTuongTu;
    private ArrayList<Listing> arrayListTinTuongTu;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    String idListing = "";
    String idUser = "";
    ArrayList<Favorite> arrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ViewPager viewPager;
    private ArrayList<Image> list;
    private viewPaperImage adapter;
    private LinearLayout linearLayout;
    private TextView[] textView;


    private LinearLayout linearHuongnha, linearbancong, Linearsotang, linearphongngu, lineartoilet, linearnoitthat, linearphaply, lineartuongtu;

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
        dateEnd = (TextView) findViewById(R.id.dateEnd);
        phaply = (TextView) findViewById(R.id.phaply);
        nameUser = (TextView) findViewById(R.id.nameUser);
        phoneUser = (TextView) findViewById(R.id.phoneUser);
        emailUser = (TextView) findViewById(R.id.emailUser);
        noithat = (TextView) findViewById(R.id.noithat);
        numberFloor = (TextView) findViewById(R.id.numberFloor);
        huongbancong = (TextView) findViewById(R.id.huongbancong);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(viewListener);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        imageViewFavorite = (ImageView) findViewById(R.id.imageViewFavorite);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        linearHuongnha = (LinearLayout) findViewById(R.id.linearHuongnha);
        linearbancong = (LinearLayout) findViewById(R.id.linearbancong);
        linearphongngu = (LinearLayout) findViewById(R.id.linearphongngu);
        Linearsotang = (LinearLayout) findViewById(R.id.Linearsotang);
        lineartoilet = (LinearLayout) findViewById(R.id.lineartoilet);
        linearnoitthat = (LinearLayout) findViewById(R.id.linearnoitthat);
        linearphaply = (LinearLayout) findViewById(R.id.linearphaply);
        lineartuongtu = (LinearLayout) findViewById(R.id.lineartuongtu);

        recyviews = (RecyclerView) findViewById(R.id.recyviews);
        arrayListTinTuongTu = new ArrayList<>();
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyviews.setLayoutManager(gridLayoutManager);
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @SuppressLint("SetTextI18n")
    public void GetInfor() {
        Intent intent = (Intent) getIntent();
        Listing listing = (Listing) intent.getParcelableExtra("thongtinchitiet");

        idListing = listing.getIdListing();
        titileName.setText(listing.getTitle());
        price.setText(listing.getPrice() + " " + listing.getUnit());
        inforDescription.setText(listing.getDescription());
        Acreage.setText(listing.getAcreage() + " M2 ");
        if (listing.getAddressDetail().equals("")) {
            address.setText(listing.getAddress());
        } else {
            address.setText(listing.getAddressDetail());
        }
        Species.setText(listing.getIdSpecies());
        directionHouse.setText(listing.getDirectionHouse());
        huongbancong.setText(listing.getDirectionBancoly());
        String sotang = listing.getFloors();
        Log.d("sotang", sotang);
        if (Integer.parseInt(sotang) == 0) {
            Linearsotang.setVisibility(View.GONE);
        } else {
            numberFloor.setText(listing.getFloors());
        }
        String phongngu = listing.getBedroom();
        if (Integer.parseInt(phongngu) == 0) {
            linearphongngu.setVisibility(View.GONE);
        } else {
            NumberBed.setText(listing.getBedroom());
        }
        String toilet = listing.getToilet();
        if (Integer.parseInt(toilet) == 0) {
            lineartoilet.setVisibility(View.GONE);
        } else {
            NumberToilet.setText(listing.getToilet());
        }

        if ((TextUtils.isEmpty(listing.getFurniture()))) {
            linearnoitthat.setVisibility(View.GONE);
        } else {
            noithat.setText(listing.getFurniture());
        }

        DatePost.setText(listing.getDateStart());
        dateEnd.setText(listing.getDateEnd());
        if ((TextUtils.isEmpty(listing.getJuridical()))) {
            linearphaply.setVisibility(View.GONE);
        } else {
            phaply.setText(listing.getJuridical());
        }
        nameUser.setText(listing.getNameContact());
        phoneUser.setText("0" + listing.getPhoneContact());
        emailUser.setText(listing.getEmailContact());


        titileName.setMaxLines(3);
        titileName.setEllipsize(TextUtils.TruncateAt.END);

        GetAnh(listing.getIdListing());
        TinTuongTu(listing.getAddress(), listing.getIdListing());

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
            Toast.makeText(getApplicationContext(), "Gọi thât bại vui lòng thử lại " + ex.getMessage(),
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

    public void addDotsIndicator(int position) {
        textView = new TextView[list.size()];
        linearLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            textView[i] = new TextView(this);
            textView[i].setText(Html.fromHtml("&#8226;"));
            textView[i].setTextSize(28);
            textView[i].setTextColor(getResources().getColor(R.color.white));

            linearLayout.addView(textView[i]);

        }

        if (textView.length > 0) {
            textView[position].setTextColor(getResources().getColor(R.color.blue));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
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

    public void GetAnh(String idListing) {
        DataClient getData = APIUtils.getData();
        Call<List<Image>> callback = getData.getImage(idListing);
        callback.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                list = (ArrayList<Image>) response.body();
                if (list.size() > 0) {
                    adapter = new viewPaperImage(list, ProductDetail.this);
                    viewPager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
            }
        });
    }

    public void TinTuongTu(String address, String idListing) {
        DataClient getData = APIUtils.getData();
        Call<List<Listing>> callback = getData.getTinTuongTu(address,idListing);
        callback.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                arrayListTinTuongTu = (ArrayList<Listing>) response.body();
                if (arrayListTinTuongTu.size() == 0) {
                    lineartuongtu.setVisibility(View.GONE);
                    Toast.makeText(ProductDetail.this, "Không có tin tương tự", Toast.LENGTH_SHORT).show();
                } else {
                    adapterTinTuongTu = new AdapterTinTuongTu(ProductDetail.this, arrayListTinTuongTu);
                    recyviews.setAdapter(adapterTinTuongTu);
                    adapterTinTuongTu.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {
            }
        });
    }
}