package com.example.btngsn.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.btngsn.Adapter.SectionsPagerAdapter;
import com.example.btngsn.R;

public class HomePage extends AppCompatActivity {
    private AHBottomNavigation bottomNavigation;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(sectionsPagerAdapter);

        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        createAhBottom();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menuGH:
//                Intent intent = new Intent(getApplicationContext(),Giohang.class);
//                startActivity(intent);
//                break;
//
//        }
//        return true;
//    }

    private void createAhBottom() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_baseline_home_24);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Home", R.drawable.ic_baseline_home_24);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Home", R.drawable.ic_baseline_home_24);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));

        //mau khi chon tab
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        //mau khi chua chon tab
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBlack));

        // layout mặc định
        bottomNavigation.setCurrentItem(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                viewpager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }
}