package com.example.btngsn.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FragmentSell extends Fragment {
    private RecyclerView recyviewsellhome;
    private ListingAdapter adapterSell;
    private ArrayList<Listing> listingArrayList;


    public FragmentSell() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        recyviewsellhome = (RecyclerView) view.findViewById(R.id.recyviewsellhome);
        recyviewsellhome.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyviewsellhome.setLayoutManager(gridLayoutManager);
        getDataOne();
        return  view;
    }
    public void getDataOne(){
        String idForm = "2";
        DataClient getData = APIUtils.getData();
        Call<List<Listing>> callback = getData.getListing(idForm);
        callback.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                listingArrayList =  (ArrayList<Listing>) response.body();
                if(listingArrayList.size() > 0 ){
                    adapterSell = new ListingAdapter(getContext(),listingArrayList);
                    recyviewsellhome.setAdapter(adapterSell);
                    adapterSell.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Không có tin nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {

            }
        });
    }
}