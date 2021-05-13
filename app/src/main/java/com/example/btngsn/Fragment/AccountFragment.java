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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Activity.PostListing;
import com.example.btngsn.Login.Login;
import com.example.btngsn.Login.Registration;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    public Button login, registration;
    public LinearLayout btnLiner;
    public TextView postlisting, fullName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
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
        btnLiner = (LinearLayout) view.findViewById(R.id.btnLiner);

        initShare();
        Event();

        return view;
    }
    public void initShare() {
        sharedPreferences = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    @Subscribe
    public void loginSuccess(boolean b){
        Event();
    }
    @Subscribe
    public void Event(){
        int idspUser = Integer.parseInt(sharedPreferences.getString("idspUser",""));
        String fullname = sharedPreferences.getString("fullName","");
        Toast.makeText(getContext(), ""+fullname, Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(fullname)){
            fullName.setVisibility(View.VISIBLE);
            fullName.setText(sharedPreferences.getString("fullName",""));
            btnLiner.setVisibility(View.INVISIBLE);
        }
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

        postlisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostListing.class);
                startActivity(intent);
            }
        });
    }
}