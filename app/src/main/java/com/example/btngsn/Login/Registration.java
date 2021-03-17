package com.example.btngsn.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.btngsn.R;

public class Registration extends AppCompatActivity {
    private Button btnregistration;
    private TextView btnLogin;
    private EditText editphone;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        event();
        ActionBar();
    }

    public void init() {
        btnregistration = (Button) findViewById(R.id.btnregistration);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        editphone = (EditText) findViewById(R.id.editphone);

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
    }

    public void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}