package com.example.btngsn.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        // position + 1 vì position bắt đầu từ số 0.
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new Fragment();
                break;
            case 1:
                frag = new Fragment();
                break;
            case 2:
                frag = new Fragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";

        }
        return null;
    }
}
