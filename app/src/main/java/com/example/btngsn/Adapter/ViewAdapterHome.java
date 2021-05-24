package com.example.btngsn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btngsn.Fragment.FragmentLease;
import com.example.btngsn.Fragment.FragmentSell;

public class ViewAdapterHome extends FragmentPagerAdapter {
    public ViewAdapterHome(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentSell fragmentSell = new FragmentSell();
                return fragmentSell;
            case 1:
                FragmentLease FragmentLease = new FragmentLease();
                return FragmentLease;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Mua Bán";
            case 1:
                return "Cho Thuê";
        }
        return null;
    }
}
