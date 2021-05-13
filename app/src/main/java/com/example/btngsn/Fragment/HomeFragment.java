package com.example.btngsn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Adapter.ListingAdapter;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private LinearLayout buyhome, sellhome;
    private RecyclerView recyviewsellhome, recyviewthuehome;
    private ListingAdapter adapterSell;
    private ListingAdapter listingAdapter;
    private ArrayList<Listing> listingArrayList;
    private ArrayList<Listing> arrayList;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        buyhome = (LinearLayout) view.findViewById(R.id.buyhome);
        sellhome = (LinearLayout) view.findViewById(R.id.sellhome);
        recyviewsellhome = (RecyclerView) view.findViewById(R.id.recyviewsellhome);
        recyviewthuehome = (RecyclerView) view.findViewById(R.id.recyviewthuehome);

        onClick();
        getDataOne();
        getDataTwo();
        return view;
    }

    public void onClick(){
        buyhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductDetail.class);
                startActivity(intent);
            }
        });
    }

    public void getDataOne(){
        String idForm = "1";
        DataClient getData = APIUtils.getData();
        Call<List<Listing>> callback = getData.getListing(idForm);
        callback.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                listingArrayList =  (ArrayList<Listing>) response.body();
                if(listingArrayList.size() > 0 ){
                    adapterSell = new ListingAdapter(getContext(),listingArrayList);
                    recyviewsellhome.setHasFixedSize(true);
                    recyviewsellhome.setAdapter(adapterSell);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyviewsellhome.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getDataTwo(){
        String idForm = "2";
        DataClient getData = APIUtils.getData();
        Call<List<Listing>> callback = getData.getListing(idForm);
        callback.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                arrayList =  (ArrayList<Listing>) response.body();
                if(arrayList.size() > 0 ){
                    listingAdapter = new ListingAdapter(getContext(),arrayList);
                    recyviewthuehome.setHasFixedSize(true);
                    recyviewthuehome.setAdapter(listingAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyviewthuehome.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });
    }

}