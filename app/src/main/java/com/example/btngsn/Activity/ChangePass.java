package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btngsn.Login.Registration;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import org.simple.eventbus.Subscriber;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class ChangePass extends AppCompatActivity {
    private EditText edtpassold, editPass, editPass1;
    private Button save;
    private Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userName;
    String passWord;
    String pass;
    String passTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        init();
        ActionBar();
        initShare();
        Event();
    }

    public void init() {
        edtpassold = (EditText) findViewById(R.id.edtpassold);
        editPass = (EditText) findViewById(R.id.editPass);
        editPass1 = (EditText) findViewById(R.id.editPass1);
        save = (Button) findViewById(R.id.save);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void initShare() {
        sharedPreferences = getApplicationContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
    @Subscriber(tag = "loginSuccess")
    public void loginSuccess(boolean b) {
        Event();
    }

    public void Event() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });
    }

    public void Update() {
        userName = sharedPreferences.getString("userName", "");
        Log.d("userName", userName);
        passWord = edtpassold.getText().toString();
        Log.d("userName", edtpassold.getText().toString());
        pass = editPass.getText().toString();
        Log.d("userName", editPass.getText().toString());
        passTwo = editPass1.getText().toString();
        if (pass.equals(passTwo)) {
            DataClient updatePass = APIUtils.getData();
            retrofit2.Call<String> callback = updatePass.updatePass(userName, passWord, pass);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        int result = Integer.parseInt(response.body());
                        if (result == 1) {
                            Toast.makeText(ChangePass.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else if (result == 0) {
                            Toast.makeText(ChangePass.this, "Mật khẩu cũ không đúng", Toast.LENGTH_LONG).show();
                        } else if (result == 2) {
                            Toast.makeText(ChangePass.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("loi", t.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải trùng mật khâu ", Toast.LENGTH_SHORT).show();
        }

    }
}