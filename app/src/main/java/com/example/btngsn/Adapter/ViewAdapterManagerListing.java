package com.example.btngsn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btngsn.Fragment.ChoXacNhan;
import com.example.btngsn.Fragment.DaDangTin;
import com.example.btngsn.Fragment.FragmentLease;
import com.example.btngsn.Fragment.FragmentSell;

public class ViewAdapterManagerListing extends FragmentStatePagerAdapter {

    public ViewAdapterManagerListing(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChoXacNhan();
            case 1:
                return new DaDangTin();
            default:
                return new ChoXacNhan();
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
                titile = "Chờ Đăng Tin";
                break;
            case 1:
                titile =  "Đã Đăng Tin";
                break;
        }
        return titile;
    }
}
