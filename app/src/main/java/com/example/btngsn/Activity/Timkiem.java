package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btngsn.Adapter.ListTimKiem;
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

    RecyclerView listView;
    ListTimKiem mRcvAdapter;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        recyclerView = (RecyclerView) findViewById(R.id.recyviews);
        listView = (RecyclerView) findViewById(R.id.listView);
        timkiemArray = new ArrayList<>();
        data = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(linearLayoutManager);

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

            if(idForm.equals("2")){
                data.add("Nhà đất bán");
            }  else if (idForm.equals("3")){
                data.add("Nhà đất thuê");
            }
            if(!idSpecies.isEmpty()){
                data.add(idSpecies);
            }
            if(!directionHouse.isEmpty()){
                data.add(directionHouse);
            }
            if(!sophongngu.isEmpty()){
                data.add(sophongngu);
            }
            if(!gia1.isEmpty()){
                if(gia1.equals("30")){
                    data.add("> " +gia1+ " "+ donvi);
                } else {
                    data.add(gia1 + "-" +gia2 + " " +donvi);
                }
            }
            if(!donvi.isEmpty() && gia1.isEmpty() && gia2.isEmpty()){
                data.add(donvi);
            }
            if(!dientich1.isEmpty()){
                if(dientich1.equals("300"))
                {
                    data.add(">= " +dientich1+ " M2");
                } else {
                    data.add(dientich1 + " " + dientich2 + " M2");
                }
            }
            mRcvAdapter = new ListTimKiem(data);
            listView.setAdapter(mRcvAdapter);
        }
    }

    public void getData() {
        if(address.isEmpty() && idForm.isEmpty() && idSpecies.isEmpty() && directionHouse.isEmpty() &&  gia2.isEmpty()
                && dientich2.isEmpty() && donvi.isEmpty()){
            listView.setVisibility(View.GONE);
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
                    Toast.makeText(Timkiem.this, "Không có tin nào", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}