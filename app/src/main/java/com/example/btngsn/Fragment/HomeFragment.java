package com.example.btngsn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.R;

public class HomeFragment extends Fragment {
    private LinearLayout buyhome, sellhome;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        buyhome = (LinearLayout) view.findViewById(R.id.buyhome);
        sellhome = (LinearLayout) view.findViewById(R.id.sellhome);

        onClick();
        return view;
    }

    public void onClick(){
        buyhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductDetail.class);
                startActivity(intent);
            }
        });
    }

}