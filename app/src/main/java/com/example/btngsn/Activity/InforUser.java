package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.btngsn.Login.Login;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import org.simple.eventbus.Subscriber;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforUser extends AppCompatActivity {
    private EditText edtHoten, edtdiachi, edtsdt, edtemail, edtcmnd, edtFace, edtWeb;
    private Button btnLuulai;
    private RadioButton checkNam, checkNu;
    private RadioGroup radioGroup;
    private Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String idUser;
    String fullName;
    String numberPhone;
    String Email;
    String CMND;
    String address;
    String linkFace;
    String linkWeb;
    int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_user);
        init();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.checknam:
                        break;
                    case R.id.checknu:
                        break;
                }
            }
        });
        ActionBar();
        initShare();
        Getdata();
        Event();
    }

    public void init() {
        edtHoten = (EditText) findViewById(R.id.edtHoten);
        edtdiachi = (EditText) findViewById(R.id.edtdiachi);
        edtsdt = (EditText) findViewById(R.id.edtsdt);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtcmnd = (EditText) findViewById(R.id.edtcmnd);
        edtFace = (EditText) findViewById(R.id.edtFace);
        edtWeb = (EditText) findViewById(R.id.edtWeb);

        btnLuulai = (Button) findViewById(R.id.btnLuulai);

        checkNam = (RadioButton) findViewById(R.id.checknam);
        checkNu = (RadioButton) findViewById(R.id.checknu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Subscriber(tag = "loginSuccess")
    public void loginSuccess(boolean b) {
        Getdata();
        UpdateInfor();
    }

    public void Getdata() {
        idUser = sharedPreferences.getString("idUser", "");
        edtHoten.setText(sharedPreferences.getString("fullName", ""));
        edtdiachi.setText(sharedPreferences.getString("address", ""));
        edtsdt.setText(sharedPreferences.getString("numberPhone", ""));
        edtemail.setText(sharedPreferences.getString("Email", ""));
        edtcmnd.setText(sharedPreferences.getString("CMND", ""));
        edtFace.setText(sharedPreferences.getString("linkFace", ""));
        edtWeb.setText(sharedPreferences.getString("linkWeb", ""));

        String sex = sharedPreferences.getString("sex","");
        if (sex.equals("1")){
            checkNam.setChecked(true);
        } if (sex.equals("2")){
            checkNu.setChecked(true);
        }
    }

    public void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Event() {
        btnLuulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNam.isChecked()){
                    sex = 1;
                    UpdateInfor();
                } else
                if(checkNu.isChecked()){
                    sex = 2;
                    UpdateInfor();
                }
            }
        });
    }

    public void UpdateInfor() {
        String userName = sharedPreferences.getString("userName","");
        String pasWord = sharedPreferences.getString("passWord","");
        String idspUser = sharedPreferences.getString("idUser","");
        String sodu = sharedPreferences.getString("sodu", "");
        fullName = edtHoten.getText().toString();
        address = edtdiachi.getText().toString();
        numberPhone = edtsdt.getText().toString();
        Email = edtemail.getText().toString();
        CMND = edtcmnd.getText().toString();
        linkFace = edtFace.getText().toString();
        linkWeb = edtWeb.getText().toString();
        DataClient updateInfor = APIUtils.getData();
        retrofit2.Call<String> callback = updateInfor.updateInfor(idUser, fullName, numberPhone, Email, CMND, address, linkFace, linkWeb, sex);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    int result = Integer.parseInt(response.body());
                    if (result == 1) {
                        Toast.makeText(InforUser.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Login.updateCaced(getApplicationContext(), idUser, fullName, userName, pasWord, numberPhone, Email,
                                idspUser, CMND,address,linkFace,linkWeb,sodu, String.valueOf(sex));
                    } else if (result == 2) {
                        Toast.makeText(InforUser.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }
}