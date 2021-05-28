package com.example.btngsn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btngsn.Fragment.FragmentLease;
import com.example.btngsn.Fragment.FragmentSell;

public class ViewAdapterHome extends FragmentStatePagerAdapter {
    public ViewAdapterHome(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentSell();
            case 1:
                return new FragmentLease();
            default:
                return new FragmentSell();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titile = "";
        switch (position){
            case 0:
                titile = "Mua Bán";
                break;
            case 1:
                titile =  "Cho Thuê";
                break;
        }
        return titile;
    }
}
