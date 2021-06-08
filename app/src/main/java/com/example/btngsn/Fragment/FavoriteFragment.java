package com.example.btngsn.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
     private RecyclerView recyclerView;
     private ListingAdapter adapter;
     ArrayList<Listing> arrayList;

     SharedPreferences sharedPreferences;
     SharedPreferences.Editor editor;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyviews);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        initShare();
        getData();
        return  view;
    }

    public void initShare() {
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void getData(){
        String idUser = sharedPreferences.getString("idUser","");
        DataClient getData = APIUtils.getData();
        Call<List<Listing>> callback = getData.getFavoriteHome(idUser);
        callback.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                arrayList =  (ArrayList<Listing>) response.body();
                if(arrayList.size() > 0 ){
                    adapter = new ListingAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Không có tin nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {

            }
        });
    }
}