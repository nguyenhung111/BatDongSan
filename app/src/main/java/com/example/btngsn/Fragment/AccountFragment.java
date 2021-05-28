package com.example.btngsn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Activity.ChangePass;
import com.example.btngsn.Activity.ContactListing;
import com.example.btngsn.Activity.InforUser;
import com.example.btngsn.Activity.ManageListing;
import com.example.btngsn.Activity.PostListing;
import com.example.btngsn.Activity.Thongtincoban;
import com.example.btngsn.Login.Login;
import com.example.btngsn.Login.Registration;
import com.example.btngsn.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class AccountFragment extends Fragment {
    public Button login, registration, btnlogout, btnNaptien;
    public TextView postlisting, fullName, sdt,managerinfor,changepass,managerlisting;
    public ImageView imgAva;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout linearDX,btnLiner;
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
        managerinfor = (TextView) view.findViewById(R.id.managerinfor);
        sdt = (TextView) view.findViewById(R.id.sdt);
        managerlisting = (TextView) view.findViewById(R.id.managerlisting);
        changepass = (TextView) view.findViewById(R.id.changepass);

        imgAva = (ImageView) view.findViewById(R.id.imgAva);

        btnlogout = (Button) view.findViewById(R.id.btnlogout);
        btnNaptien = (Button) view.findViewById(R.id.btnNaptien);

        linearDX = (LinearLayout) view.findViewById(R.id.linearDX);
        btnLiner = (LinearLayout) view.findViewById(R.id.btnLiner);

        initShare();
        checkData();
        Event();
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
            linearDX.setVisibility(View.VISIBLE);
            btnLiner.setVisibility(View.GONE);
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

        if(!TextUtils.isEmpty(fullname)) {
            btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().getSharedPreferences("datalogin", 0).edit().clear().apply();
                    if (!fullName.equals("")) {
                        fullName.setText("Tên khách hàng");
                        sdt.setText("Số điện thoại");
                        Toast.makeText(getContext(), " Bạn đã đăng xuất tài khoản", Toast.LENGTH_LONG).show();
                        btnLiner.setVisibility(View.VISIBLE);
                        linearDX.setVisibility(View.GONE);
                    }
                }
            });
            postlisting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Thongtincoban.class);
                    startActivity(intent);
                }
            });

            managerinfor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), InforUser.class);
                    startActivity(intent);
                }
            });
            changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ChangePass.class);
                    startActivity(intent);
                }
            });
            managerlisting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ManageListing.class);
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập hoặc đăng ký để sử dụng chức năng", Toast.LENGTH_LONG).show();
        }
    }

}