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


public class FragmentLease extends Fragment {
    private RecyclerView recyviewthuehome;
    private ListingAdapter listingAdapter;
    private ArrayList<Listing> arrayList;

    public FragmentLease() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lease, container, false);
        recyviewthuehome = (RecyclerView) view.findViewById(R.id.recyviewthuehome);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyviewthuehome.setLayoutManager(gridLayoutManager);
        getDataTwo();
        return  view;
    }
    public void getDataTwo(){
        String idForm = "3";
        DataClient getData = APIUtils.getData();
        Call<List<Listing>> callback = getData.getListing(idForm);
        callback.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                arrayList =  (ArrayList<Listing>) response.body();
                if(arrayList.size() > 0 ){
                    listingAdapter = new ListingAdapter(getContext(),arrayList);
                    recyviewthuehome.setAdapter(listingAdapter);
                    listingAdapter.notifyDataSetChanged();
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