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

import com.example.btngsn.Adapter.AdapterTaichinh;
import com.example.btngsn.Adapter.ItemAdapter;
import com.example.btngsn.Model.Taichinh;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LichSuGiaoDich extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Taichinh> arrayList;
    private AdapterTaichinh adapterTaichinh;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public LichSuGiaoDich() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich_su_giao_dich, container, false);
        init();
        arrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyviews);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        getData();
        return view;
    }

    public void init(){
        sharedPreferences = getActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void getData(){
        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query  = "";
        if(idspUser.equals("1")){
            query = "SELECT * FROM taichinh WHERE YEAR(CURRENT_DATE) - YEAR(ngaygiaodich) =0";
        }
        else {
            query = "SELECT * FROM taichinh WHERE idUser = " + idUser + " and YEAR(CURRENT_DATE) - YEAR(ngaygiaodich) =0 ";
        }

        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<List<Taichinh>> callback = dataClient.getTaichinh(query);
        callback.enqueue(new Callback<List<Taichinh>>() {
            @Override
            public void onResponse(Call<List<Taichinh>> call, Response<List<Taichinh>> response) {
                arrayList = (ArrayList<Taichinh>) response.body();
                if(arrayList.size()>0){
                    adapterTaichinh = new AdapterTaichinh(getContext(), arrayList);
                    recyclerView.setAdapter(adapterTaichinh);
                    adapterTaichinh.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Taichinh>> call, Throwable t) {

            }
        });
    }

}