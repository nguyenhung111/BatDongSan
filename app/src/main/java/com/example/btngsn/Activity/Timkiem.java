package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Timkiem extends AppCompatActivity {
    String address = "";
    String donvi = "";
    String idForm = "";
    String idSpecies = "";
    String directionHouse = "";
    String sophongngu = "";
    String gia1, gia2;
    String dientich1, dientich2;

    RecyclerView recyclerView;
    private ListingAdapter adapterSell;
    private ArrayList<Listing> timkiemArray;

    private ListingAdapter adapter;
    private ArrayList<Listing> listingArrayList;

    ArrayList<String> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyviews);
        timkiemArray = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        getDulieu();
        getData();
    }

    public void getDulieu() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            idForm = bundle.getString("hinhthuc", "");

            address = bundle.getString("diachi", "");

            idSpecies = bundle.getString("loaitin", "");

            directionHouse = bundle.getString("huongnha", "");

            donvi = bundle.getString("donvi", "");

            gia1 = bundle.getString("gia1", "");

            gia2 = bundle.getString("gia2", "");

            dientich1 = bundle.getString("dientich1", "");

            dientich2 = bundle.getString("dientich2", "");

            sophongngu = bundle.getString("sophongngu", "");

            list.add(idForm);
            list.add(address);
            list.add(idSpecies);
            list.add(directionHouse);
            list.add(donvi);
            list.add(gia1);
            list.add(gia2);
            list.add(dientich1);
            list.add(dientich2);
            list.add(sophongngu);
            
        }
    }

    public void getData() {
        if(address.isEmpty() && idForm.isEmpty() && idSpecies.isEmpty() && directionHouse.isEmpty() && gia1.isEmpty() && gia2.isEmpty()
                && dientich1.isEmpty() && dientich2.isEmpty()){
            DataClient getData = APIUtils.getData();
            Call<List<Listing>> callback = getData.getAll();
            callback.enqueue(new Callback<List<Listing>>() {
                @Override
                public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                    listingArrayList =  (ArrayList<Listing>) response.body();
                    if(listingArrayList.size() > 0 ){
                        adapter = new ListingAdapter(getApplicationContext(),listingArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getApplicationContext(), "Không có tin nào", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Listing>> call, Throwable t) {

                }
            });
        } else {
            DataClient getData = APIUtils.getData();
            Call<List<Listing>> callback = getData.getSearch(address, idForm, idSpecies, directionHouse, donvi, gia1, gia2, dientich1, dientich2, sophongngu);
            callback.enqueue(new Callback<List<Listing>>() {
                @Override
                public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                    timkiemArray = (ArrayList<Listing>) response.body();
                    if(timkiemArray.size() > 0){
                        adapterSell = new ListingAdapter(getApplicationContext(), timkiemArray);
                        recyclerView.setAdapter(adapterSell);
                        adapterSell.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Listing>> call, Throwable t) {

                }
            });
        }
    }
}