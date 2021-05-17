package com.example.btngsn.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Home.HomePage;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.simple.eventbus.EventBus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editaccount, editpass;
    private TextView forgetpass, nowregistration;
    private Button btnlogin, btnLoginGmail;
    public String account, password;
    private Button btnLoginFace;
    private LoginButton btnLoginFace1;
    CallbackManager callbackManager;
    public SharedPreferences luutaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.btngsn.Login",                  //Insert your own package name.
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        luutaikhoan = getApplicationContext().getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        init();
        ActionTool();
        event();
        LoginFace();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.backlogin);
        editaccount = (EditText) findViewById(R.id.editaccount);
        editpass = (EditText) findViewById(R.id.editpass);
        forgetpass = (TextView) findViewById(R.id.forgetpass);
        nowregistration = (TextView) findViewById(R.id.dangkyngay);
        btnlogin = (Button) findViewById(R.id.btnLogin);
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

    public void event() {
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

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    public void LoginFace() {
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

    public void Login() {
        account = editaccount.getText().toString();
        password = editpass.getText().toString();

        if (account.length() > 0 && password.length() > 0) {
            DataClient loginData = APIUtils.getData();
            Call<List<User>> callback = loginData.Login(account, password);
            callback.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    ArrayList<User> userArrayList = (ArrayList<User>) response.body();
                    if (userArrayList.size() > 0) {
                        String idUser = userArrayList.get(0).getIdUser();
                        String fullName = userArrayList.get(0).getFullName();
                        String userName = userArrayList.get(0).getUserName();
                        String pasWord = userArrayList.get(0).getPassWord();
                        String numberPhone = userArrayList.get(0).getNumberPhone();
                        String Email = userArrayList.get(0).getEmail();
                        String idspUser = userArrayList.get(0).getIdspUser();
                        String CMND = userArrayList.get(0).getCmnd();

                        updateCaced(getApplicationContext(), idUser, fullName, userName, pasWord, numberPhone, Email, idspUser, CMND);

                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        intent.putExtra("dataUser", userArrayList);
                        startActivity(intent);

                        EventBus.getDefault().post(true,"loginSuccess");
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    public void updateCaced(Context context, String idUser, String fullName, String userName, String passWord, String numberPhone, String Email, String idspUser, String CMND) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedangnhap.edit();
        editor.putString("idUser", idUser);
        editor.putString("fullName", fullName);
        editor.putString("userName", userName);
        editor.putString("passWord", passWord);
        editor.putString("numberPhone", numberPhone);
        editor.putString("Email", Email);
        editor.putString("idspUser", idspUser);
        editor.putString("CMND", CMND);
        editor.commit();
    }
}