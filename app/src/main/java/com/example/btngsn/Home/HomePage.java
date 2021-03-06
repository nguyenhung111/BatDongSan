package com.example.btngsn.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.btngsn.Fragment.AccountFragment;
import com.example.btngsn.Fragment.FavoriteFragment;
import com.example.btngsn.Fragment.HomeFragment;
import com.example.btngsn.Fragment.mua_thue;
import com.example.btngsn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(naviListen);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, new HomeFragment());
        fragmentTransaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener naviListen = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectFrame = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectFrame = new HomeFragment();
                    break;
                case R.id.nav_mua_thue:
                    selectFrame = new mua_thue();
                    break;
                case R.id.nav_favorite:
                    selectFrame = new FavoriteFragment();
                    break;
                case R.id.nav_account:
                    selectFrame = new AccountFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selectFrame).commit();
            return  true;
        }
    };
}