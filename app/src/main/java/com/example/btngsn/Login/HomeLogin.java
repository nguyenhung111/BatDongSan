package com.example.btngsn.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.btngsn.Adapter.sliderAdapter;
import com.example.btngsn.Home.HomePage;
import com.example.btngsn.R;

public class HomeLogin extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] textView;
    private sliderAdapter sliderAdapter;
    private Button login,registration;
    private TextView countine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);
        init();
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
        click();
    }

    public void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        sliderAdapter = new sliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        login = (Button) findViewById(R.id.dangnhap);
        registration = (Button) findViewById(R.id.dangky);
        countine = (TextView) findViewById(R.id.countine);
    }
    public void addDotsIndicator(int position){
        textView = new TextView[4];
        linearLayout.removeAllViews();
        for(int i = 0 ; i < textView.length;i++){
            textView[i] = new TextView(this);
            textView[i].setText(Html.fromHtml("&#8226;"));
            textView[i].setTextSize(28);
            textView[i].setTextColor(getResources().getColor(R.color.xam));

            linearLayout.addView(textView[i]);

        }

        if(textView.length > 0){
            textView[position].setTextColor(getResources().getColor(R.color.blue));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    public void click(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeLogin.this, Login.class);
                startActivity(intent);
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeLogin.this, Registration.class);
                startActivity(intent);
            }
        });
        countine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeLogin.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}