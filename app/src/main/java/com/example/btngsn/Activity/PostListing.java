package com.example.btngsn.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Adapter.directionAdapter;
import com.example.btngsn.Adapter.formAdapter;
import com.example.btngsn.Adapter.speciesAdapter;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.viewDirection;
import com.example.btngsn.Model.viewForm;
import com.example.btngsn.Model.viewSpecies;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListing extends AppCompatActivity {
    private Button countine, trutang, truphong, trutoilet, congtang, congphong, congtoilet;
    private TextView valuestang, valuesphong, valuestoilet;
    private Spinner spnhinhthuc, spnloai, spnhuongnha, spnbancong;
    private EditText edttieude, edtdientich, edtdiachi, edtmota, edtnoithat, edtphaply;
    private ImageView imageView;
    final int REQUEST_CHOOSE_PHOTO = 321;
    String realpath = "";

    ArrayList<viewForm> arrayListForm;
    formAdapter adapterForm;
    viewForm viewFormIntent;

    ArrayList<viewSpecies> arrayListSpecies;
    speciesAdapter speciesAdapter;
    viewSpecies viewSpeciesIntent;

    ArrayList<viewDirection> directionArrayList;
    directionAdapter directionAdapter;
    viewDirection viewDirectionIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_listing);
        init();
        event();
        ChoosePhoto();
        getForm();
        getSpecies();
        getDirection();
        getBalconly();
    }

    public void init() {
        countine = (Button) findViewById(R.id.countine);
        trutang = (Button) findViewById(R.id.tangtru);
        truphong = (Button) findViewById(R.id.truphong);
        trutoilet = (Button) findViewById(R.id.trutoilet);
        congtang = (Button) findViewById(R.id.tangcong);
        congphong = (Button) findViewById(R.id.congphong);
        congtoilet = (Button) findViewById(R.id.congtoilet);

        valuestang = (TextView) findViewById(R.id.valuestang);
        valuesphong = (TextView) findViewById(R.id.valuesphong);
        valuestoilet = (TextView) findViewById(R.id.valuestoilet);

        spnhinhthuc = (Spinner) findViewById(R.id.spnhinhthuc);
        spnloai = (Spinner) findViewById(R.id.siploai);
        spnhuongnha = (Spinner) findViewById(R.id.spnhuongnha);
        spnbancong = (Spinner) findViewById(R.id.spnbancong);

        edttieude = (EditText) findViewById(R.id.edttieude);
        edtdientich = (EditText) findViewById(R.id.edtdientich);
        edtdiachi = (EditText) findViewById(R.id.edtdiachi);
        edtmota = (EditText) findViewById(R.id.edtmota);
        edtnoithat = (EditText) findViewById(R.id.edtnoithat);
        edtphaply = (EditText) findViewById(R.id.edtphaply);

        imageView = (ImageView) findViewById(R.id.imageView);

    }

    public void event() {
        countine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnh();
                senddata();
            }
        });
        congtang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(valuestang.getText().toString()) + 1;
                valuestang.setText(String.valueOf(soluong));
            }
        });
        trutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(valuestang.getText().toString()) > 0) {
                    int soluong = Integer.parseInt(valuestang.getText().toString()) - 1;
                    valuestang.setText(String.valueOf(soluong));
                }
            }
        });
    }

    public void senddata() {
        clickonitem();
        String hinhthuc = viewFormIntent.getNameForm();
        String loai = viewSpeciesIntent.getNameSpecies();
        String huongnha = viewDirectionIntent.getNameDirection();
        String bancong = viewDirectionIntent.getNameDirection();

        Intent myIntent = new Intent(PostListing.this, ContactListing.class);
        Bundle bundle = new Bundle();
        byte[] Anh = ArrayFromImageView(imageView);
        bundle.putString("tieude", edttieude.getText().toString().trim());
        bundle.putString("dientich", edtdientich.getText().toString().trim());
        bundle.putString("diachi", edtdiachi.getText().toString().trim());
        bundle.putString("mota", edtmota.getText().toString().trim());
        bundle.putString("noithat", edtnoithat.getText().toString().trim());
        bundle.putString("phaply", edtphaply.getText().toString().trim());
        bundle.putString("sotang", valuestang.getText().toString().trim());
        bundle.putString("sophong", valuesphong.getText().toString().trim());
        bundle.putString("sotoilet", valuestoilet.getText().toString().trim());
        bundle.putByteArray("hinhanh", Anh);
        //Đưa Bundle vào Intent
        myIntent.putExtra("MyPackage", bundle);
        //Mở Activity ResultActivity
        startActivity(myIntent);
    }

    public void sendAnh() {
        byte[] Anh = ArrayFromImageView(imageView);
        Log.d("hung", String.valueOf(Anh));
        //tao ket noi
        DataClient dataClient = APIUtils.getData();

        retrofit2.Call<String> callback = dataClient.sendAnh(String.valueOf(Anh));
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    String massege = response.body();
                    Log.d("mess", massege);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("aa", t.getMessage());
            }
        });
    }


    private void ChoosePhoto() {
        Log.d("daipv", handlerPermission() + "");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO && data != null) {
                Uri imageUri = data.getData();
                realpath = getRealPathFromURI(imageUri);
                try {
                    InputStream is = (InputStream) getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bitmap);
                    imageView.setBackground(null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] ArrayFromImageView(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private boolean handlerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("daipv", "Permission is granted");
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("daipv", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("daipv", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void getForm() {
        arrayListForm = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<viewForm>> callback = getData.getForm();
        callback.enqueue(new Callback<List<viewForm>>() {
            @Override
            public void onResponse(Call<List<viewForm>> call, Response<List<viewForm>> response) {
                arrayListForm = (ArrayList<viewForm>) response.body();
                if (arrayListForm.size() > 0) {
                    adapterForm = new formAdapter(PostListing.this, arrayListForm);
                    spnhinhthuc.setAdapter(adapterForm);
                }
            }

            @Override
            public void onFailure(Call<List<viewForm>> call, Throwable t) {

            }
        });
    }

    public void getSpecies() {
        arrayListSpecies = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<viewSpecies>> callback = getData.getSpecies();
        callback.enqueue(new Callback<List<viewSpecies>>() {
            @Override
            public void onResponse(Call<List<viewSpecies>> call, Response<List<viewSpecies>> response) {
                arrayListSpecies = (ArrayList<viewSpecies>) response.body();
                if (arrayListSpecies.size() > 0) {
                    speciesAdapter = new speciesAdapter(PostListing.this, arrayListSpecies);
                    spnloai.setAdapter(speciesAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<viewSpecies>> call, Throwable t) {

            }
        });

    }

    public void getDirection() {
        directionArrayList = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<viewDirection>> callback = getData.getDirection();
        callback.enqueue(new Callback<List<viewDirection>>() {
            @Override
            public void onResponse(Call<List<viewDirection>> call, Response<List<viewDirection>> response) {
                directionArrayList = (ArrayList<viewDirection>) response.body();
                if (directionArrayList.size() > 0) {
                    directionAdapter = new directionAdapter(PostListing.this, directionArrayList);
                    spnhuongnha.setAdapter(directionAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<viewDirection>> call, Throwable t) {

            }

        });
    }
    public void getBalconly() {
        directionArrayList = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<viewDirection>> callback = getData.getDirection();
        callback.enqueue(new Callback<List<viewDirection>>() {
            @Override
            public void onResponse(Call<List<viewDirection>> call, Response<List<viewDirection>> response) {
                directionArrayList = (ArrayList<viewDirection>) response.body();
                if (directionArrayList.size() > 0) {
                    directionAdapter = new directionAdapter(PostListing.this, directionArrayList);
                    spnbancong.setAdapter(directionAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<viewDirection>> call, Throwable t) {

            }

        });
    }

    public void clickonitem() {
        spnhinhthuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewFormIntent = new viewForm(arrayListForm.get(position).getIdForm(), arrayListForm.get(position).getNameForm());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewSpeciesIntent = new viewSpecies(arrayListSpecies.get(position).getIdSpecies(), arrayListSpecies.get(position).getNameSpecies());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnhuongnha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewDirectionIntent = new viewDirection(directionArrayList.get(position).getIdHouse(), directionArrayList.get(position).getIdHouse());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnbancong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewDirectionIntent = new viewDirection(directionArrayList.get(position).getIdHouse(), directionArrayList.get(position).getIdHouse());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}