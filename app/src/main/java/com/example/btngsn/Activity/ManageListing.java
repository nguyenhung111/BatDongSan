package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.btngsn.Adapter.ItemAdapter;
import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Adapter.ViewAdapterHome;
import com.example.btngsn.Adapter.ViewAdapterManagerListing;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageListing extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_listing);

        tabLayout = (TabLayout) findViewById(R.id.tabDN);
        viewPager = (ViewPager) findViewById(R.id.viewPaper);
        ViewAdapterManagerListing viewAdapterHome = new ViewAdapterManagerListing(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewAdapterHome);
        tabLayout.setupWithViewPager(viewPager);

    }

}