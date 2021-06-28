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

import com.example.btngsn.Adapter.ItemAdapter;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaDangTin extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Listing> arrayList;
    ItemAdapter adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public DaDangTin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_da_dang_tin, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyviews);
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        initShare();
        getData();
        return  view;
    }
    public void initShare() {
        sharedPreferences = getActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void getData() {
        String idUser = sharedPreferences.getString("idUser", "");
        String idspUser = sharedPreferences.getString("idspUser", "");
        if (idspUser.equals("1")) {
            DataClient getData = APIUtils.getData();
            Call<List<Listing>> callback = getData.getListingForAdmin("2","3");
            callback.enqueue(new Callback<List<Listing>>() {
                @Override
                public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                    arrayList = (ArrayList<Listing>) response.body();
                    if (arrayList.size() > 0) {
                        adapter = new ItemAdapter(getContext(), arrayList);
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
        } else {
            DataClient getData = APIUtils.getData();
            Call<List<Listing>> callback = getData.getListingForId(idUser,"2","3");
            callback.enqueue(new Callback<List<Listing>>() {
                @Override
                public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                    arrayList = (ArrayList<Listing>) response.body();
                    if (arrayList.size() > 0) {
                        adapter = new ItemAdapter(getContext(), arrayList);
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
}