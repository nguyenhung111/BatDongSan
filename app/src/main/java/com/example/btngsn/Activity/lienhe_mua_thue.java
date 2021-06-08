package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.example.btngsn.Retrofit.FileUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lienhe_mua_thue extends AppCompatActivity {
    String tieude, noidung, hinhthuc, loaidat, diachi, dientich, gia, tenlienhe, diachilienhe, dienthoai, email, image;
    Button dangtin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public TextView startDate, endDate, ngaydang;
    public EditText edtnamecontact, addresscontact, phonecontact, editEmail;

    String currentDate, date;
    DateFormat df;
    long getDaysDiff;
    Date date1 = null;
    Date date2 = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienhe_mua_thue);


        init();
        getData();
        initShare();
        Chonngay();
        df = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        date = df.format(cal.getTime());
        endDate.setText(date);
        startDate.setText(currentDate);
        endDate.setText(date);
        startDate.setText(currentDate);
        String hientai = startDate.getText().toString();
        String ketthuc = endDate.getText().toString();
        try {
            date1 = df.parse(hientai);
            date2 = df.parse(ketthuc);
            long getDiff = date2.getTime() - date1.getTime();

            getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
            ngaydang.setText(String.valueOf(getDaysDiff));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PostListing();
    }

    public void init() {
        edtnamecontact = (EditText) findViewById(R.id.edtnamecontact);
        addresscontact = (EditText) findViewById(R.id.addresscontact);
        phonecontact = (EditText) findViewById(R.id.phonecontact);
        editEmail = (EditText) findViewById(R.id.emailcontact);

        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        ngaydang = (TextView) findViewById(R.id.ngaydang);

        dangtin = (Button) findViewById(R.id.tindang);
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            tieude = bundle.getString("tieude", "");
            hinhthuc = bundle.getString("hinhthuc", "");
            loaidat = bundle.getString("loai", "");
            dientich = bundle.getString("dientich", "");
            gia = bundle.getString("giatien", "");
            diachi = bundle.getString("diachi", "");
            noidung = bundle.getString("mota", "");
            Toast.makeText(this, ""+noidung, Toast.LENGTH_SHORT).show();
            image = bundle.getString("hinhanh", "");
            Toast.makeText(this, ""+image, Toast.LENGTH_SHORT).show();
            Log.d("image", image);
        }

    }

    public void Chonngay() {
        Calendar calendar = Calendar.getInstance();
        long getdate = calendar.getTimeInMillis();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(lienhe_mua_thue.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        startDate.setText(simpleDateFormat.format(calendar.getTime()));
                        endDate.setText(date);
                        tinhngay();
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(getdate);
                datePickerDialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(lienhe_mua_thue.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        endDate.setText(simpleDateFormat.format(calendar.getTime()));
                        startDate.setText(currentDate);
                        tinhngay();
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(getdate);
                datePickerDialog.show();
            }
        });
    }

    public void tinhngay() {
        String hientai = startDate.getText().toString();
        String ketthuc = endDate.getText().toString();
        try {
            date1 = df.parse(hientai);
            date2 = df.parse(ketthuc);
            long getDiff = date2.getTime() - date1.getTime();
            getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
            ngaydang.setText(String.valueOf(getDaysDiff));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void PostListing() {
        dangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenlienhe = edtnamecontact.getText().toString();
                dienthoai = phonecontact.getText().toString();
                email = editEmail.getText().toString();
                String diachi = addresscontact.getText().toString();
                String idUser = sharedPreferences.getString("idUser", "");
                //duong dan file
                File file = new File(image);
                String file_path = file.getAbsolutePath();
                String[] mangtenfile = file_path.split("\\.");

                file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
                //tao ket noi
                DataClient dataClient = APIUtils.getData();

                retrofit2.Call<String> callback = dataClient.UpLoadImage(body);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null) {
                            String massege = response.body();
                            DataClient inserData = APIUtils.getData();
                            retrofit2.Call<String> callback = inserData.postMuaThue(tieude, noidung, APIUtils.Base_Url + "image/" + massege, hinhthuc, loaidat, diachi, dientich, gia, tenlienhe,
                                    diachilienhe, dienthoai, email, idUser, currentDate, date,1);
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String idMuathue = response.body();
                                    if(idMuathue.equals("1")) {
                                        Toast.makeText(lienhe_mua_thue.this, "Thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("aa", t.getMessage());
                    }
                });
            }
        });
    }
}