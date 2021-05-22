package com.example.btngsn.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Home.HomePage;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    private Button btnregistration;
    private TextView btnLogin;
    private EditText edtUsername, edtEmail, edtPassword, edtFullname, edtpass;
    private String userName, Email, passWord, fullName, pass;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        ActionBar();
        event();
    }

    public void init() {
        btnregistration = (Button) findViewById(R.id.btnregistration);
        btnLogin = (TextView) findViewById(R.id.btnLogin);

        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtEmail = (EditText) findViewById(R.id.edtemail);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        edtFullname = (EditText) findViewById(R.id.edtfullname);
        edtpass = (EditText) findViewById(R.id.edtpass);


        toolbar = findViewById(R.id.toolbar);
    }

    public void event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });

        btnregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostRegistration();
            }
        });
    }

    public void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24) );
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void PostRegistration() {
        userName = edtUsername.getText().toString();
        Email = edtEmail.getText().toString();
        passWord = edtPassword.getText().toString();
        pass = edtpass.getText().toString();
        fullName = edtFullname.getText().toString();

        String numberPhone = "0";
        String idspUser = "2";
        String CMND = "0";
//
        if (fullName.length() < 6) {
            Toast.makeText(getApplicationContext(), "Bạn cần nhập đầy đủ họ tên", Toast.LENGTH_SHORT).show();
        } else if (userName.length() < 0) {
            Toast.makeText(this, "Bạn chưa nhập tài khoản", Toast.LENGTH_SHORT).show();
        } else if (edtEmail.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Nhập lại email", Toast.LENGTH_SHORT).show();
        } else if (passWord.length() < 6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải có từ 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
        } else if (passWord .equals(pass)) {
            DataClient postRegistration = APIUtils.getData();
            retrofit2.Call<String> callback = postRegistration.Registration(fullName, userName, passWord, numberPhone, Email, idspUser, CMND);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        int result = Integer.parseInt(response.body());
                        if (result == 1) {
                            Toast.makeText(Registration.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else if (result == 0) {
                            Toast.makeText(Registration.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("bbbb", t.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải trùng mật khâu ", Toast.LENGTH_SHORT).show();
        }
    }
}