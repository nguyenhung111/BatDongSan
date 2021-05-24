package com.example.btngsn.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        recyviewsellhome = (RecyclerView) view.findViewById(R.id.recyviewsellhome);
        getDataOne();
        return  view;
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

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
}