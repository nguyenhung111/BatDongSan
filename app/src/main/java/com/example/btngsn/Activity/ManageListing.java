package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.btngsn.Adapter.ItemAdapter;
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

public class ManageListing extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<Listing> arrayList;
    ItemAdapter adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_listing);

        recyclerView = (RecyclerView) findViewById(R.id.recyviews);
        arrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        getData();
        initShare();

    }

    public void initShare(){
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    public void getData(){
        String idUser = sharedPreferences.getString("idUser","");
        String idspUser = sharedPreferences.getString("idspUser","");
        if(idspUser.equals("1")){
            DataClient getData = APIUtils.getData();
            Call<List<Listing>> callback = getData.getListingForAdmin();
            callback.enqueue(new Callback<List<Listing>>() {
                @Override
                public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                    arrayList = (ArrayList<Listing>) response.body();
                    if (arrayList.size() > 0) {
                        adapter = new ItemAdapter(ManageListing.this, arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Listing>> call, Throwable t) {
                    Log.d("mes", t.getMessage());
                    Toast.makeText(getApplication(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            DataClient getData = APIUtils.getData();
            Call<List<Listing>> callback = getData.getListingForId(idUser);
            callback.enqueue(new Callback<List<Listing>>() {
                @Override
                public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                    arrayList = (ArrayList<Listing>) response.body();
                    if (arrayList.size() > 0) {
                        adapter = new ItemAdapter(ManageListing.this, arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Listing>> call, Throwable t) {
                    Log.d("mes", t.getMessage());
                    Toast.makeText(getApplication(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}