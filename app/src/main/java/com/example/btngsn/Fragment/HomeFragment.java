package com.example.btngsn.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.btngsn.Adapter.ViewAdapterHome;

import com.example.btngsn.R;

import com.google.android.material.tabs.TabLayout;



public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    private RelativeLayout relatSearch;

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
        ViewAdapterHome viewAdapterHome = new ViewAdapterHome(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewAdapterHome);
        tabLayout.setupWithViewPager(viewPager);


        relatSearch = (RelativeLayout) view.findViewById(R.id.relatSearch);
        relatSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }


}