package com.example.btngsn.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btngsn.Adapter.PhotoAdapter;
import com.example.btngsn.Adapter.directionAdapter;
import com.example.btngsn.Model.InternetConnection;
import com.example.btngsn.Model.viewDirection;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostListing extends AppCompatActivity {


    private Button countine, trutang, truphong, trutoilet, congtang, congphong, congtoilet;
    private TextView valuestang, valuesphong, valuestoilet, txttongtien;
    private Spinner spnhuongnha, spnbancong, spndonvi;
    private EditText edttieude, edtdientich, edtmota, edtnoithat, edtphaply, edtgiatien;
    private ImageView imageView;
    private RecyclerView recyclerView;
    String hinhthuc, loai, diachi, diachichitiet, urlMap;

    TextView counttieude, countmota;

    ArrayList<viewDirection> directionArrayList;
    directionAdapter directionAdapter;
    viewDirection viewDirectionIntent;
    viewDirection directionBalcony;

    private final int REQUEST_CODE_PERMISSIONS = 1;
    private final int REQUEST_CODE_READ_STORAGE = 2;
    private ArrayList<Uri> arrayList;
    private static final String TAG = PostListing.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_listing);
        init();
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            hinhthuc = bundle.getString("hinhthuc", "");
            loai = bundle.getString("loai", "");
            diachi = bundle.getString("diachi", "");
            diachichitiet = bundle.getString("diachichitiet", "");
            urlMap = bundle.getString("urlMap", "");
        }
        edttieude.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int length = edttieude.length();
                String convert = String.valueOf(length);
                counttieude.setText(convert + "/1000");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtmota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int length = edtmota.length();
                String convert = String.valueOf(length);
                countmota.setText(convert + "/3000");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EventSpiner();
        ChoosePhoto();
        getDirection();
        getBalconly();
        clickonitem();
        event();
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
        txttongtien = (TextView) findViewById(R.id.txttongtien);
        counttieude = (TextView) findViewById(R.id.counttitile);
        countmota = (TextView) findViewById(R.id.countmota);

        spnhuongnha = (Spinner) findViewById(R.id.spnhuongnha);
        spnbancong = (Spinner) findViewById(R.id.spnbancong);
        spndonvi = (Spinner) findViewById(R.id.spnloaitien);

        edttieude = (EditText) findViewById(R.id.edttieude);
        edtdientich = (EditText) findViewById(R.id.edtdientich);
        edtgiatien = (EditText) findViewById(R.id.edtgiatien);
        edtmota = (EditText) findViewById(R.id.edtmota);
        edtnoithat = (EditText) findViewById(R.id.edtnoithat);
        edtphaply = (EditText) findViewById(R.id.edtphaply);

        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recyviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void event() {
        countine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieude = edttieude.getText().toString();
                String dientich = edtdientich.getText().toString();
                String donvi = spndonvi.getSelectedItem().toString();
                String mota = edtmota.getText().toString();
                if (tieude.length() < 30) {
                    edttieude.setError("Ti??u ????? ph???i t??? 30 ?????n 99 k?? t???");
                    Toast.makeText(PostListing.this, "Vui l??ng ki???m tra ti??u ?????", Toast.LENGTH_SHORT).show();
                } else if (mota.length() < 30) {
                    edtmota.setError("N???i dung m?? t??? t??? 30 ?????n 3000 k?? t???");
                    Toast.makeText(PostListing.this, "Vui l??ng ki???m tra n???i dung", Toast.LENGTH_SHORT).show();
                } else if (!dientich.isEmpty() && !donvi.isEmpty() && !mota.isEmpty()) {
                    senddata();
                } else {
                    Toast.makeText(PostListing.this, "B???n vui l??ng nh???p ?????y ????? th??ng tin b???t bu???c", Toast.LENGTH_SHORT).show();
                }

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
        congphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(valuesphong.getText().toString()) + 1;
                valuesphong.setText(String.valueOf(soluong));
            }
        });
        truphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(valuesphong.getText().toString()) > 0) {
                    int soluong = Integer.parseInt(valuesphong.getText().toString()) - 1;
                    valuesphong.setText(String.valueOf(soluong));
                }
            }
        });
        congtoilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(valuestoilet.getText().toString()) + 1;
                valuestoilet.setText(String.valueOf(soluong));
            }
        });
        trutoilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(valuestoilet.getText().toString()) > 0) {
                    int soluong = Integer.parseInt(valuestoilet.getText().toString()) - 1;
                    valuestoilet.setText(String.valueOf(soluong));
                }
            }
        });
    }


    public void senddata() {

        String huongnha = viewDirectionIntent.getNameDirection();
        String bancong = directionBalcony.getNameDirection();
        String giatien = edtgiatien.getText().toString();
        String donvitinh = spndonvi.getSelectedItem().toString();
        txttongtien.setText(giatien.concat(donvitinh));
        String tieude = edttieude.getText().toString().trim();
        Intent myIntent = new Intent(PostListing.this, ContactListing.class);
        Bundle bundle = new Bundle();
        bundle.putString("tieude", tieude);
        bundle.putString("dientich", edtdientich.getText().toString().trim());
        bundle.putString("giatien", giatien);
        bundle.putString("donvitinh", donvitinh);
        bundle.putString("mota", edtmota.getText().toString().trim());
        bundle.putString("sotang", valuestang.getText().toString().trim());
        bundle.putString("sophongngu", valuesphong.getText().toString().trim());
        bundle.putString("sotoilet", valuestoilet.getText().toString().trim());
        bundle.putString("huongnha", huongnha);
        bundle.putString("bancong", bancong);
        bundle.putString("noithat", edtnoithat.getText().toString().trim());
        bundle.putString("phaply", edtphaply.getText().toString().trim());
        bundle.putString("sotang", valuestang.getText().toString().trim());
        bundle.putString("sophong", valuesphong.getText().toString().trim());
        bundle.putString("sotoilet", valuestoilet.getText().toString().trim());
        bundle.putString("hinhthuc", hinhthuc);
        bundle.putString("loai", loai);
        bundle.putString("diachi", diachi);
        bundle.putString("diachichitiet", diachichitiet);
        bundle.putString("urlMap", urlMap);
        bundle.putParcelableArrayList("image", arrayList);
        //????a Bundle v??o Intent
        myIntent.putExtras(bundle);
        //M??? Activity ResultActivity
        startActivity(myIntent);

    }


    private void ChoosePhoto() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askForPermission();
                } else {
                    showChooser();
                }
            }

        });
    }


    private void EventSpiner() {
        String[] donvi = new String[]{"Th???a thu??n", "Tri???u", "T???", "Tr??m ngh??n/m2", "Tri???u/m2","Tri???u/th??ng"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, donvi);
        spndonvi.setAdapter(arrayAdapter);
    }

    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());

                            try {
                                arrayList.add(imageUri);
                                PhotoAdapter mAdapter = new PhotoAdapter(PostListing.this, arrayList);
                                recyclerView.setAdapter(mAdapter);

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
                        Log.i(TAG, "Uri = " + uri.toString());

                        try {
                            arrayList.add(uri);
                            PhotoAdapter mAdapter = new PhotoAdapter(PostListing.this, arrayList);
                            recyclerView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
            }
        }
    }


    /**
     * Runtime Permission
     */
    private void askForPermission() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            /* Ask for permission */
            // need to request permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(this.findViewById(android.R.id.content),
                        "Vui l??ng c???p quy???n ????? ghi d??? li???u v??o sdcard",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        v -> ActivityCompat.requestPermissions(PostListing.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_PERMISSIONS)).show();
            } else {
                /* Request for permission */
                ActivityCompat.requestPermissions(PostListing.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            }

        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showChooser();
            } else {
                // Permission Denied
                Toast.makeText(PostListing.this, "Quy???n b??? t??? ch???i!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
        spnhuongnha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewDirectionIntent = new viewDirection(directionArrayList.get(position).getIdHouse(), directionArrayList.get(position).getNameDirection());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnbancong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                directionBalcony = new viewDirection(directionArrayList.get(position).getIdHouse(), directionArrayList.get(position).getNameDirection());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}