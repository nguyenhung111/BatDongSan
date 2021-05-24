package com.example.btngsn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Adapter.ViewAdapterHome;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabDN);
        viewPager = (ViewPager) view.findViewById(R.id.viewPaper);


        ViewAdapterHome viewpaperAdapterDangNhap = new ViewAdapterHome(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(viewpaperAdapterDangNhap);
        viewpaperAdapterDangNhap.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}