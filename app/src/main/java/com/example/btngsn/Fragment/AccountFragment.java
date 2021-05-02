package com.example.btngsn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.btngsn.Activity.PostListing;
import com.example.btngsn.Login.Login;
import com.example.btngsn.Login.Registration;
import com.example.btngsn.R;

public class AccountFragment extends Fragment {
    private Button login, registration;
    private TextView postlisting;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        login = (Button) view.findViewById(R.id.loginAcc);
        registration = (Button) view.findViewById(R.id.registrationAcc);
        postlisting = (TextView) view.findViewById(R.id.postlisting);

        Event();
        return view;
    }

    public void Event(){
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