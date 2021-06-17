package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.btngsn.R;

public class NapTienTaiKhoan extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String idUser = "";
    TextView ma,noidung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien_tai_khoan);
        ma = (TextView) findViewById(R.id.ma);
        noidung = (TextView) findViewById(R.id.noidung);

        initShare();
        Noidung();
    }

    @SuppressLint("CommitPrefEdits")
    public void initShare(){
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    @SuppressLint("SetTextI18n")
    public void Noidung(){
        idUser = sharedPreferences.getString("idUser", "");
        ma.setText("BDS"+idUser+"<Nội dung thanh toán>");
        ma.setTextColor(Color.BLACK);
        String content = "nhập đứng mã BDS"+idUser+" ở đầu nội dung chuyển khoản";
        SpannableString s = new SpannableString(content);
        s.setSpan(new ForegroundColorSpan(Color.RED),0,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        noidung.setText("BDS"+idUser+" là mã chuyển khoản riêng của bạn.Hệ thống của chúng tôi sẽ căn cứ trên mã này để xử lý giao dịch. Do vậy bạn " +
                "vui lòng "+s+" để việc xác nhận giao dịch được nhanh chóng và chính xác");
    }
}