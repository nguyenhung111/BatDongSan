package com.example.btngsn.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.btngsn.R;

public class Login extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editaccount, editpass;
    private TextView forgetpass, nowregistration;
    private Button btnlogin, btnLoginFace, btnLoginGmail;
    public String account,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        ActionTool();
        event();
    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.backlogin);
        editaccount = (EditText) findViewById(R.id.editaccount);
        editpass = (EditText) findViewById(R.id.editpass);
        forgetpass = (TextView) findViewById(R.id.forgetpass);
        nowregistration = (TextView) findViewById(R.id.dangkyngay);
        btnlogin = (Button) findViewById(R.id.dangky);
        btnLoginFace = (Button) findViewById(R.id.btnLoginface);
        btnLoginGmail = (Button) findViewById(R.id.btnLoginGmail);


    }
    private void ActionTool() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void event(){
        nowregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }

}