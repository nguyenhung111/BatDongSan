package com.example.btngsn.Login;

import androidx.annotation.Nullable;
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

import com.example.btngsn.Home.HomePage;
import com.example.btngsn.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editaccount, editpass;
    private TextView forgetpass, nowregistration;
    private Button btnlogin, btnLoginGmail,loginF;
    public String account,password;
    private Button btnLoginFace;
    private LoginButton btnLoginFace1;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        ActionTool();
        event();
        LoginFace();
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
        btnLoginFace1 = (LoginButton) findViewById(R.id.btnLoginface1);


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
        btnLoginFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginFace1.performClick();
            }
        });
    }

    public void LoginFace(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
                AccessToken accessToken = loginResult.getAccessToken();
                

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}