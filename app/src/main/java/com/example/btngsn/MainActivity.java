package com.example.btngsn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btngsn.Home.HomePage;
import com.example.btngsn.Login.HomeLogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity {
    private static int Splash_screen = 3000;

    //Variables
    Animation topAnim, botAnim;
    ImageView iamge;
    TextView logo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_animation);

        iamge = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);

        iamge.setAnimation(topAnim);
        logo.setAnimation(botAnim);
        initShare();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }, Splash_screen);
    }
    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    @Subscribe
    public void loginSuccess(boolean b){
        check();
    }
    @Subscribe
    public void check(){
        String fullname = sharedPreferences.getString("fullName","");
        if(!TextUtils.isEmpty(fullname)){
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(MainActivity.this, HomeLogin.class);
            startActivity(intent);
            finish();
        }
    }

}