package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.btngsn.Adapter.BuyRentAdapter;
import com.example.btngsn.Model.BuyRent;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quanlymuathue extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<BuyRent> arrayList;
    BuyRentAdapter adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlymuathue);

        recyclerView = (RecyclerView) findViewById(R.id.recyviews);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getData();
    }

    public void getData() {
        String idUser = sharedPreferences.getString("idUser","");
        String query = "SELECT * FROM tbl_muathue WHERE trangthai = 2 and idUser = "+idUser+" ";
        DataClient getData = APIUtils.getData();
        Call<List<BuyRent>> callback = getData.getBuyRent(query);
        callback.enqueue(new Callback<List<BuyRent>>() {
            @Override
            public void onResponse(Call<List<BuyRent>> call, Response<List<BuyRent>> response) {
                arrayList = (ArrayList<BuyRent>) response.body();
                if (arrayList.size() > 0) {
                    adapter = new BuyRentAdapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BuyRent>> call, Throwable t) {

            }
        });
    }
}