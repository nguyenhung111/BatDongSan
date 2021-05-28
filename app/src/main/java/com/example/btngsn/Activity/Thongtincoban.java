package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.btngsn.Adapter.formAdapter;
import com.example.btngsn.Adapter.speciesAdapter;
import com.example.btngsn.Model.DataCity;
import com.example.btngsn.Model.DataDistrict;
import com.example.btngsn.Model.DataWard;
import com.example.btngsn.Model.viewForm;
import com.example.btngsn.Model.viewSpecies;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Thongtincoban extends AppCompatActivity {
    private Spinner spnhinhthuc, spnloai, spnCity, spnDistrict, spnWard;
    private EditText edtdiachi, editdiachi, edtUrlmap;
    private Button countine;
    ArrayList<viewForm> arrayListForm;
    formAdapter adapterForm;
    viewForm viewFormIntent;

    ArrayList<viewSpecies> arrayListSpecies;
    speciesAdapter speciesAdapter;
    viewSpecies viewSpeciesIntent;

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
        getForm();
        getSpecies();
        spinnerCity();
        chooseSpinnerCity();
        chooseSpinnerWard();
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

    public void getForm() {
        arrayListForm = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<viewForm>> callback = getData.getForm();
        callback.enqueue(new Callback<List<viewForm>>() {
            @Override
            public void onResponse(Call<List<viewForm>> call, Response<List<viewForm>> response) {
                arrayListForm = (ArrayList<viewForm>) response.body();
                if (arrayListForm.size() > 0) {
                    adapterForm = new formAdapter(Thongtincoban.this, arrayListForm);
                    spnhinhthuc.setAdapter(adapterForm);
                    spnhinhthuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            viewFormIntent = new viewForm(arrayListForm.get(position).getIdForm(), arrayListForm.get(position).getNameForm());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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
                    speciesAdapter = new speciesAdapter(Thongtincoban.this, arrayListSpecies);
                    spnloai.setAdapter(speciesAdapter);
                    spnloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            viewSpeciesIntent = new viewSpecies(arrayListSpecies.get(position).getIdSpecies(), arrayListSpecies.get(position).getNameSpecies());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<viewSpecies>> call, Throwable t) {

            }
        });

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
                        Log.d("address", address);
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
        String hinhthuc = viewFormIntent.getIdForm();
        String loai = viewSpeciesIntent.getNameSpecies();
        Intent myIntent = new Intent(Thongtincoban.this, PostListing.class);
        Bundle bundle = new Bundle();
        bundle.putString("hinhthuc", hinhthuc);
        bundle.putString("loai", loai);
        bundle.putString("diachi", editdiachi.getText().toString().trim());
        bundle.putString("diachichitiet", editdiachi.getText().toString().trim());
        bundle.putString("urlMap", edtUrlmap.getText().toString().trim());
        myIntent.putExtras(bundle);
        //Mở Activity ResultActivity
        startActivity(myIntent);
    }
}