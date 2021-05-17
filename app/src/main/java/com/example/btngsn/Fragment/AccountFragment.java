package com.example.btngsn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Activity.PostListing;
import com.example.btngsn.Activity.chosePhoto;
import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Login.Login;
import com.example.btngsn.Login.Registration;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.Model.test;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.squareup.picasso.Picasso;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    public Button login, registration, btnlogout;
    public TextView postlisting, fullName, sdt;
    public ImageView imgAva;
    Login loginClass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String idspUser;
    String fullname;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        EventBus.getDefault().register(this);
        login = (Button) view.findViewById(R.id.loginAcc);
        registration = (Button) view.findViewById(R.id.registrationAcc);
        postlisting = (TextView) view.findViewById(R.id.postlisting);
        fullName = (TextView) view.findViewById(R.id.fullName);
        sdt = (TextView) view.findViewById(R.id.sdt);
        imgAva = (ImageView) view.findViewById(R.id.imgAva);

        btnlogout = (Button) view.findViewById(R.id.btnlogout);

        initShare();
        Event();
        checkData();
        return view;
    }

    public void initShare() {
        sharedPreferences = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Subscriber(tag = "loginSuccess")
    public void loginSuccess(boolean b) {
        Event();
        checkData();
    }


    public void checkData() {
        idspUser = sharedPreferences.getString("idspUser", "");
        fullname = sharedPreferences.getString("fullName", "");
        if (!TextUtils.isEmpty(fullname)) {
            fullName.setText(sharedPreferences.getString("fullName", ""));
            sdt.setText("0" + sharedPreferences.getString("numberPhone", ""));
            btnlogout.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            registration.setVisibility(View.INVISIBLE);
        }
    }

    public void Event() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Registration.class);
                startActivity(intent);
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().getSharedPreferences("datalogin", 0).edit().clear().apply();
                if (!fullName.equals("")) {
                    fullName.setText("Tên khách hàng");
                    sdt.setText("Số điện thoại");
                    Toast.makeText(getContext(), " Bạn đã đăng xuất tài khoản", Toast.LENGTH_LONG).show();
                    login.setVisibility(View.VISIBLE);
                    registration.setVisibility(View.VISIBLE);
                    btnlogout.setVisibility(View.GONE);
                }
            }
        });
        postlisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostListing.class);
                startActivity(intent);
            }
        });


    }

//    public void getAnh() {
//
//        DataClient getData = APIUtils.getData();
//        Call<List<test>> callback = getData.getAnh();
//        callback.enqueue(new Callback<List<test>>() {
//            @Override
//            public void onResponse(Call<List<test>> call, Response<List<test>> response) {
//                ArrayList<test> arrayList = (ArrayList<test>) response.body();
//                if(arrayList.size() >0){
//                    String url = "http://localhost/batdongsan/image/IMG_20201124_1102131621058496528.jpg";
//                    //String url = arrayList.get(0).getHinhanh();
//                    String[] link =   url.split("/");
//                    for(String t : link)
//                        String.valueOf(t);
//                    String anh = link[5];
//                    Log.d("anh",anh);
//                    Picasso.get().load("http://192.168.1.12/batdongsan/image/"+anh).into(imgAva);
//                    Log.d("image", arrayList.get(0).getHinhanh());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<test>> call, Throwable t) {
//
//            }
//        });
//    }
}