package com.example.btngsn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btngsn.Fragment.ChiTietSoDu;
import com.example.btngsn.Fragment.ChoXacNhan;
import com.example.btngsn.Fragment.DaDangTin;
import com.example.btngsn.Fragment.LichSuGiaoDich;
import com.example.btngsn.Fragment.Thongke;

public class ViewAdapterManageMoney extends FragmentStatePagerAdapter {

    public ViewAdapterManageMoney(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Thongke();
            case 1:
                return new ChiTietSoDu();
            case 2:
                return new LichSuGiaoDich();
            default:
                return new ChiTietSoDu();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titile = "";
        switch (position){
            case 0:
                titile = "Thống kê";
                break;
            case 1:
                titile =  "Chi tiết số dư";
                break;
            case 2:
                titile =  "Lịch sử giao dịch";
                break;
        }
        return titile;
    }
}
