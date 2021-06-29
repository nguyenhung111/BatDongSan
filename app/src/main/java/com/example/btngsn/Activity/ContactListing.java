package com.example.btngsn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Home.HomePage;
import com.example.btngsn.Model.InternetConnection;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.example.btngsn.Retrofit.FileUtils;
import com.google.android.material.snackbar.Snackbar;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListing extends AppCompatActivity {

    String titile, idForm, idSpecies, acreage, price, unit, address, description, floors,
            bedroom, toilet, directionHouse, directionBancoly, furniture, juridical, nameContact, phoneContact, idUser, dateStart, dateEnd;
    String image, addressDetai, urlmap, emailcontact;
    public EditText edtnamecontact, addresscontact, phonecontact, editEmail;
    public TextView startDate, endDate, txtdongia, ngaydang, txtVat, txtMoney, txtSum;
    public Button tindang;
    Spinner spnloaitin;
    String currentDate, date;
    String vat;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ArrayList<Uri> arrayList;
    DateFormat df;
    long getDaysDiff;
    Date date1 = null;
    Date date2 = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    int sodutaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_listing);

        EventBus.getDefault().register(this);

        init();
        arrayList = new ArrayList<>();
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initShare();
        EventSpiner();
        getData();
        getSodu();
        Event();
    }

    public void init() {
        edtnamecontact = (EditText) findViewById(R.id.edtnamecontact);
        addresscontact = (EditText) findViewById(R.id.addresscontact);
        phonecontact = (EditText) findViewById(R.id.phonecontact);
        editEmail = (EditText) findViewById(R.id.emailcontact);
        spnloaitin = (Spinner) findViewById(R.id.spnloaitin);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        txtdongia = (TextView) findViewById(R.id.txtdongia);
        ngaydang = (TextView) findViewById(R.id.ngaydang);
        txtVat = (TextView) findViewById(R.id.txtVat);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        txtSum = (TextView) findViewById(R.id.txtSum);
        tindang = (Button) findViewById(R.id.tindang);
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void Event() {
        tindang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtnamecontact.getText().toString().isEmpty()) {
                    Toast.makeText(ContactListing.this, "Vui lòng nhập đầy đủ tên", Toast.LENGTH_SHORT).show();
                }  else if(phonecontact.getText().toString().length() < 10) {
                    Toast.makeText(ContactListing.this, "Nhập đủ 10 số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    PostListing();
                }
            }
        });
    }

    @Subscriber(tag = "loginSuccess")
    public void loginSuccess(boolean b) {
        getData();
        PostListing();
    }

    private void EventSpiner() {
        ArrayList<String> donvi = new ArrayList<>();
        donvi.add("Tin thường");
        donvi.add("Tin vip");
        donvi.add("Tin đặc biệt");
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, donvi);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnloaitin.setAdapter(arrayAdapter);

        spnloaitin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String loai = donvi.get(i);
                if (loai.equals("Tin thường")) {
                    txtdongia.setText("2000");
                    int ngay = Integer.parseInt(String.valueOf(getDaysDiff));
                    int tien = (int) (2000 * ngay);
                    vat = String.valueOf(tien * 10 / 100);
                    txtVat.setText(vat + " Đồng");
                    txtMoney.setText(String.valueOf(tien) + " Đồng");
                    int gia = tien + (tien * 10 / 100);
                    txtSum.setText(String.valueOf(gia));
                } else if (loai.equals("Tin vip")) {
                    txtdongia.setText("3000");
                    int tien = (int) (3000 * getDaysDiff);
                    vat = String.valueOf(tien * 10 / 100);
                    txtVat.setText(vat + " Đồng");
                    txtMoney.setText(String.valueOf(tien) + " Đồng");
                    int gia = tien + (tien * 10 / 100);
                    txtSum.setText(String.valueOf(gia));
                } else {
                    txtdongia.setText("2500");
                    int tien = (int) (2500 * getDaysDiff);
                    vat = String.valueOf(tien * 10 / 100);
                    txtVat.setText(vat + " Đồng");
                    txtMoney.setText(String.valueOf(tien) + " Đồng");
                    int gia = tien + (tien * 10 / 100);
                    txtSum.setText(String.valueOf(gia));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getData() {
        ngaydang.setText(String.valueOf(getDaysDiff));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            titile = bundle.getString("tieude", "");
            idForm = bundle.getString("hinhthuc", "");
            idSpecies = bundle.getString("loai", "");
            acreage = bundle.getString("dientich", "");
            price = bundle.getString("giatien", "");
            unit = bundle.getString("donvitinh", "");
            address = bundle.getString("diachi", "");
            addressDetai = bundle.getString("diachichitiet", "");
            description = bundle.getString("mota", "");
            image = bundle.getString("hinhanh");
            floors = bundle.getString("sotang", "");
            bedroom = bundle.getString("sophongngu", "");
            toilet = bundle.getString("sotoilet", "");
            directionHouse = bundle.getString("huongnha", "");
            directionBancoly = bundle.getString("bancong", "");
            furniture = bundle.getString("noithat", "");
            juridical = bundle.getString("phaply", "");
            urlmap = bundle.getString("urlMap", "");
            arrayList = bundle.getParcelableArrayList("image");
        }

    }


    private void PostListing() {
        int sodu = Integer.parseInt(txtSum.getText().toString());

        if (sodutaikhoan >= sodu) {
            nameContact = edtnamecontact.getText().toString();
            phoneContact = phonecontact.getText().toString();
            emailcontact = editEmail.getText().toString();
            idUser = sharedPreferences.getString("idUser", "");
            //duong dan file
            File file = new File(FileUtils.getPath(ContactListing.this, arrayList.get(0)));
            String file_path = file.getAbsolutePath();
            String[] mangtenfile = file_path.split("\\.");

            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

            //xác nhận kiểu dữ liệu file
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // convert sang key để truyền lên server
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
                        retrofit2.Call<String> callback = inserData.PostListing(titile, idForm, idSpecies, acreage, price, unit, address, addressDetai,
                                APIUtils.Base_Url + "image/" + massege, description, floors, bedroom, toilet, directionHouse, directionBancoly, furniture, juridical,
                                nameContact, phoneContact, emailcontact, idUser,startDate.getText().toString(), endDate.getText().toString(), spnloaitin.getSelectedItem().toString(), urlmap, sodu);
                        callback.enqueue(new Callback<String>() {
                            //trả về kết quả gì
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String idListing = response.body();
                                if (Integer.parseInt(idListing) > 0) {
                                    if (arrayList != null) {
                                        for (int i = 0; i < arrayList.size(); i++) {
                                            File file = new File(FileUtils.getPath(ContactListing.this, arrayList.get(i)));
                                            String file_path = file.getAbsolutePath();
                                            String[] mangtenfile = file_path.split("\\.");

                                            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

                                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                                            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

                                            DataClient dataClient = APIUtils.getData();
                                            retrofit2.Call<String> callback = dataClient.UpLoadImage(body);
                                            callback.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    if (response != null) {
                                                        String massege = response.body();
                                                        Log.d("mass",massege);
                                                        DataClient post = APIUtils.getData();
                                                        retrofit2.Call<String> postImage = post.postImage(APIUtils.Base_Url + "image/" + massege, idListing);
                                                        postImage.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                String result = response.body();
                                                                if (result.equals("1")) {
                                                                    Toast.makeText(ContactListing.this, "Thành công", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(ContactListing.this, HomePage.class);
                                                                    startActivity(intent);
                                                                    finish();
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

                                                }
                                            });
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("tin", t.getMessage());
                            }
                        });
                    }
                }
                // client không kết nối đc server xử lý tại đây
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("aa", t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Số dư của bạn không đủ để đăng tin", Toast.LENGTH_SHORT).show();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ContactListing.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ContactListing.this, new DatePickerDialog.OnDateSetListener() {
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
            int dongia = Integer.parseInt(txtdongia.getText().toString());
            int ngay = Integer.parseInt(String.valueOf(getDaysDiff));
            int tien = (int) (dongia * ngay);
            vat = String.valueOf(tien * 10 / 100);
            txtVat.setText(vat + " Đồng");
            txtMoney.setText(String.valueOf(tien) + " Đồng");
            int gia = tien + (tien * 10 / 100);
            txtSum.setText(String.valueOf(gia));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getSodu() {
        String username = sharedPreferences.getString("userName", "");
        String password = sharedPreferences.getString("passWord", "");
        if (!username.equals("") && !password.equals("")) {
            DataClient loginData = APIUtils.getData();
            Call<List<User>> callback = loginData.Login(username, password);
            callback.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    ArrayList<User> userArrayList = (ArrayList<User>) response.body();
                    if (userArrayList.size() > 0) {
                        sodutaikhoan = Integer.parseInt(userArrayList.get(0).getSodu());
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                }
            });
        }

    }

}