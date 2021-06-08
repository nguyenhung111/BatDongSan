package com.example.btngsn.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.btngsn.Adapter.BuyRentAdapter;
import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Model.BuyRent;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mua_thue extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    ArrayList<BuyRent> arrayList;
    BuyRentAdapter adapter;

    public mua_thue() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mua_thue, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyviews);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        getData();
        return view;
    }

    public void getData() {
        DataClient getData = APIUtils.getData();
        Call<List<BuyRent>> callback = getData.getBuyRent();
        callback.enqueue(new Callback<List<BuyRent>>() {
            @Override
            public void onResponse(Call<List<BuyRent>> call, Response<List<BuyRent>> response) {
                arrayList = (ArrayList<BuyRent>) response.body();
                if (arrayList.size() > 0) {
                    adapter = new BuyRentAdapter(getContext(), arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Không có tin nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BuyRent>> call, Throwable t) {

            }
        });
    }
}