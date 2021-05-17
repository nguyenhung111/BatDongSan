package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListing extends AppCompatActivity {
    String titile, idForm, idSpecies, acreage, price, address, description, floors,
            bedroom, toilet, idHouse, idBancoly, furniture, juridical, nameContact, phoneContact, idUser, dateStart, dateEnd;
    String image;
    public EditText edtnamecontact, addresscontact, phonecontact, emailcontact;
    public TextView startDate, endDate;
    public Button tindang;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_listing);

        EventBus.getDefault().register(this);
        //MyPackage
        init();
        initShare();
        getData();

    }

    public void init() {
        edtnamecontact = (EditText) findViewById(R.id.edtnamecontact);
        addresscontact = (EditText) findViewById(R.id.addresscontact);
        phonecontact = (EditText) findViewById(R.id.phonecontact);
        emailcontact = (EditText) findViewById(R.id.emailcontact);

        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);

        tindang = (Button) findViewById(R.id.tindang);
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Subscriber(tag = "loginSuccess")
    public void loginSuccess(boolean b) {
        getData();
    }

    public void getData() {
        String idUser = sharedPreferences.getString("idUser", "");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            titile = bundle.getString("tieude", "");
            idForm = bundle.getString("hinhthuc", "");
            idSpecies = bundle.getString("loai", "");
            acreage = bundle.getString("dientich", "");
            price = bundle.getString("giatien", "");
            address = bundle.getString("diachi", "");
            description = bundle.getString("mota", "");
            image = bundle.getString("hinhanh");
            floors = bundle.getString("sotang", "");
            bedroom = bundle.getString("sophongngu", "");
            toilet = bundle.getString("sotoilet", "");
            idHouse = bundle.getString("huongnha", "");
            idBancoly = bundle.getString("bancong", "");
            furniture = bundle.getString("noithat", "");
            juridical = bundle.getString("phaply", "");
        }
//        byte[] Anh = getBytesFromBitmap(Uri.parse(image));
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        String date = df.format(cal.getTime());
        endDate.setText(date);
        startDate.setText(currentDate);


    }

    public byte[] getBytesFromBitmap(Uri mUri) {
        ByteArrayOutputStream stream = null;
        try {
            InputStream is = (InputStream) getContentResolver().openInputStream(mUri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    public void PostListing() {

        File file = new File(image);

        //duong dan file
        String file_path = file.getAbsolutePath();
        String[] mangtenfile = file_path.split("\\.");

        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Log.d("requestBody", String.valueOf(requestBody));

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
        Log.d("file_path", String.valueOf(body));
        //tao ket noi
        DataClient dataClient = APIUtils.getData();

        retrofit2.Call<String> callback = dataClient.UpLoadImage(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    String massege = response.body();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("aa", t.getMessage());
            }
        });
    }
}