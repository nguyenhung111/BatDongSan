package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.btngsn.Adapter.CitySpinnerAdapter;
import com.example.btngsn.Adapter.DistrictSpinnerAdapter;
import com.example.btngsn.Adapter.WardSpinnerAdapter;
import com.example.btngsn.Model.DataCity;
import com.example.btngsn.Model.DataDistrict;
import com.example.btngsn.Model.DataWard;
import com.example.btngsn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Thongtincoban extends AppCompatActivity {
    private Spinner spnhinhthuc, spnloai, spnCity, spnDistrict, spnWard;
    private EditText edtdiachi, editdiachi, edtUrlmap;
    private Button countine;

    private WardSpinnerAdapter wardSpinnerAdapter;
    private CitySpinnerAdapter spinnerAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;

    private DataCity dataCity;
    private DataDistrict dataDistrict;
    private DataWard dataWard;

    private List<DataCity> cityList = new ArrayList<>();
    private List<DataDistrict> districtList;
    private List<DataWard> dataWardList;

    private String idAddress;

    private String city, district, ward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thontincoban);

        init();
        spinnerCity();
        chooseSpinnerCity();
        chooseSpinnerWard();
        EventSpinner();
        clickonitem();
    }

    public void init() {
        spnhinhthuc = (Spinner) findViewById(R.id.spnhinhthuc);
        spnloai = (Spinner) findViewById(R.id.siploai);

        spnDistrict = (Spinner) findViewById(R.id.spnquanhuyen);
        spnCity = (Spinner) findViewById(R.id.spntinhTP);
        spnWard = (Spinner) findViewById(R.id.spnxaphuong);

        edtdiachi = (EditText) findViewById(R.id.edtdiachi);
        editdiachi = (EditText) findViewById(R.id.editdiachi);
        edtUrlmap = (EditText) findViewById(R.id.edtUrlmap);

        countine= (Button) findViewById(R.id.countine);
    }


    public void clickonitem() {
        countine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sendata();
            }
        });

    }

    public void spinnerCity() {
        AndroidNetworking.get("https://thongtindoanhnghiep.co/api/city")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray city = response.getJSONArray("LtsItem");
                            for (int i = 0; i < city.length(); i++) {
                                dataCity = new DataCity(city.getJSONObject(i));
                                cityList.add(dataCity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        spinnerAdapter = new CitySpinnerAdapter(cityList, Thongtincoban.this);
                        spnCity.setAdapter(spinnerAdapter);
                    }


                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void chooseSpinnerCity() {
        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dataCity = cityList.get(i);
                idAddress = dataCity.getId();
                Log.d("idAddress", idAddress);
                city = dataCity.getTitle();
                //choose Spinner District
                districtList = new ArrayList<>();
                AndroidNetworking.get("https://thongtindoanhnghiep.co/api/city/" + idAddress + "/district")
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int j = 0; j < response.length(); j++) {
                                    try {
                                        dataDistrict = new DataDistrict(response.getJSONObject(j));
                                        districtList.add(dataDistrict);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                districtSpinnerAdapter = new DistrictSpinnerAdapter(districtList, Thongtincoban.this);
                                spnDistrict.setAdapter(districtSpinnerAdapter);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void chooseSpinnerWard() {
        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataDistrict = districtList.get(position);
                idAddress = dataDistrict.getId();
                district = dataDistrict.getTitle();
                //choose ward
                dataWardList = new ArrayList<>();
                AndroidNetworking.get("https://thongtindoanhnghiep.co/api/district/" + idAddress + "/ward")
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        dataWard = new DataWard(response.getJSONObject(i));
                                        dataWardList.add(dataWard);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                wardSpinnerAdapter = new WardSpinnerAdapter(dataWardList, Thongtincoban.this);
                                spnWard.setAdapter(wardSpinnerAdapter);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

                spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        dataWard = dataWardList.get(position);
                        ward = dataWard.getTitle();
                        String address = ward.concat(" "+ district.concat(" "+city));
                        edtdiachi.setText(address);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Sendata() {
        String hinhthuc = "";
        if(spnhinhthuc.getSelectedItem().toString().equals("Bán đất")){
            hinhthuc = "2";
        } else  if(spnhinhthuc.getSelectedItem().toString().equals("Cho thuê")){
            hinhthuc = "3";
        }
        String loai = spnloai.getSelectedItem().toString().trim();
        String diachi = edtdiachi.getText().toString().trim();
        String diachichitiet = editdiachi.getText().toString().trim();
        String urlMap = edtUrlmap.getText().toString().trim();
        //!taikhoan.isEmpty() || !matkhau.isEmpty()
        if(!hinhthuc.isEmpty() || !loai.isEmpty() || !diachi.isEmpty()){
            Intent myIntent = new Intent(Thongtincoban.this, PostListing.class);
            Bundle bundle = new Bundle();
            bundle.putString("hinhthuc", hinhthuc);
            bundle.putString("loai", loai);
            bundle.putString("diachi", diachi);
            bundle.putString("diachichitiet", diachichitiet);
            bundle.putString("urlMap", urlMap);
            myIntent.putExtras(bundle);
            //Mở Activity ResultActivity
            startActivity(myIntent);
        } else {
            Toast.makeText(this, "Bạn vui lòng nhập đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
        }
    }

    public void EventSpinner(){
        String[] hinththuc = new String[]{"Bán đất", "Cho thuê"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.my_select_item, hinththuc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnhinhthuc.setAdapter(arrayAdapter);

        String[]loaitin = new String[]{"Căn hộ chung cư","Nhà riêng","Nhà biệt thự, liền kề","Nhà mặt phố","Đất nền dự án","Bán đất",
                "Trang trại,khu nghỉ dưỡng","Kho,nhà xưởng","Loại bất động sản khác"};
        ArrayAdapter<String> arrayLoaitin = new ArrayAdapter<String>(this, R.layout.my_select_item, loaitin);
        arrayLoaitin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnloai.setAdapter(arrayLoaitin);
    }
}