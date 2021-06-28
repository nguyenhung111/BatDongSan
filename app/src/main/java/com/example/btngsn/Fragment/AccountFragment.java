package com.example.btngsn.Fragment;

import android.annotation.SuppressLint;
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
import com.example.btngsn.Activity.Infor_mua_thue;
import com.example.btngsn.Activity.ManageListing;
import com.example.btngsn.Activity.ManageMoney;
import com.example.btngsn.Activity.NapTienTaiKhoan;
import com.example.btngsn.Activity.PostListing;
import com.example.btngsn.Activity.Thongtincoban;
import com.example.btngsn.Activity.quanlymuathue;
import com.example.btngsn.Home.HomePage;
import com.example.btngsn.Login.Login;
import com.example.btngsn.Login.Registration;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    public Button login, registration, btnlogout, btnNaptien;
    public TextView postlisting, fullName, sdt, managerinfor, changepass, managerlisting, post_mua_thue, sodu,manger_mua_thue;
    public ImageView imgAva;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout linearDX, btnLiner;
    String idspUser;
    String fullname;

    private TextView informoney;
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
        post_mua_thue = (TextView) view.findViewById(R.id.post_mua_thue);
        sodu = (TextView) view.findViewById(R.id.sodu);
        informoney = (TextView) view.findViewById(R.id.informoney);
        manger_mua_thue = (TextView) view.findViewById(R.id.manger_mua_thue);

        imgAva = (ImageView) view.findViewById(R.id.imgAva);

        btnlogout = (Button) view.findViewById(R.id.btnlogout);
        btnNaptien = (Button) view.findViewById(R.id.btnNaptien);

        linearDX = (LinearLayout) view.findViewById(R.id.linearDX);
        btnLiner = (LinearLayout) view.findViewById(R.id.btnLiner);

        initShare();
        checkData();
        Event();
        getData();
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
        getData();
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

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().getSharedPreferences("datalogin", 0).edit().clear().apply();
                if (!fullName.equals("")) {
                    fullName.setText("Tên khách hàng");
                    sdt.setText("Số điện thoại");
                    sodu.setText("Số dư");
                    Toast.makeText(getContext(), " Bạn đã đăng xuất tài khoản", Toast.LENGTH_LONG).show();
                    btnLiner.setVisibility(View.VISIBLE);
                    linearDX.setVisibility(View.GONE);
                }
            }
        });
        postlisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), Thongtincoban.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }
            }
        });

        managerinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), InforUser.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }
            }
        });
        informoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageMoney.class);
                startActivity(intent);
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), ChangePass.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }
            }
        });
        managerlisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), ManageListing.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }

            }
        });

        post_mua_thue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), Infor_mua_thue.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnNaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), NapTienTaiKhoan.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }

            }
        });

        manger_mua_thue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(fullname)) {
                    Intent intent = new Intent(getContext(), quanlymuathue.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập để sử dụng chức năng", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void getData() {
        if (!TextUtils.isEmpty(fullname)) {
            String username = sharedPreferences.getString("userName", "");
            String password = sharedPreferences.getString("passWord", "");
            if (!username.equals("") && !password.equals("")) {
                DataClient loginData = APIUtils.getData();
                Call<List<User>> callback = loginData.Login(username, password);
                callback.enqueue(new Callback<List<User>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        ArrayList<User> userArrayList = (ArrayList<User>) response.body();
                        if (userArrayList.size() > 0) {
                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            int taikhoan = Integer.parseInt(userArrayList.get(0).getSodu());
                            sodu.setText("Số dư : " + decimalFormat.format(taikhoan) + " Đ");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                    }
                });
            }
        }
    }
}
