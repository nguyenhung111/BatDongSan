package com.example.btngsn.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("SetTextI18n")
public class Thongke extends Fragment {
    private TextView txtTongtinbanthue,tindangban,txtTongtinmuathue,tindangmua,tientru,tongtien;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Thongke() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_thongke, container, false);


        txtTongtinbanthue = (TextView) view.findViewById(R.id.txtTongtinbanthue);
        tindangban = (TextView) view.findViewById(R.id.tindangban);
        txtTongtinmuathue = (TextView) view.findViewById(R.id.txtTongtinmuathue);
        tindangmua = (TextView) view.findViewById(R.id.tindangmua);
        tientru = (TextView) view.findViewById(R.id.tientru);
        tongtien = (TextView) view.findViewById(R.id.tongtien);

        initShare();
        getCountTongBan();
        getCountTinDang();
        getCountTongMua();
        getCountTinMua();
        Tongtien();
        TienTru();
        return  view;
    }
    @SuppressLint("CommitPrefEdits")
    public void initShare() {
        sharedPreferences = getActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void getCountTongBan(){
        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query  = "";
        if(idspUser.equals("1")){
            query = "SELECT COUNT(idUser) AS soluong FROM listing ";
        }
        else {
            query = "SELECT COUNT(idUser) AS soluong FROM listing WHERE idUser = " + idUser + " ";
        }
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.getCount(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                txtTongtinbanthue.setText(result + " tin");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    public void getCountTinDang(){
        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query  = "";
        if(idspUser.equals("1")){
            query = "SELECT COUNT(idUser) AS soluong FROM listing where trangthai = 2";
        }
        else {
            query = "SELECT COUNT(idUser) AS soluong FROM listing where trangthai = 2 and idUser = " + idUser + " ";
        }

        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.getCount(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                tindangban.setText(result + " tin");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    public void getCountTongMua(){
        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query  = "";
        if(idspUser.equals("1")){
            query = "SELECT COUNT(idUser) AS soluong FROM tbl_muathue";
        }
        else {
            query = "SELECT COUNT(idUser) AS soluong FROM tbl_muathue WHERE idUser = "+idUser+"";;
        }

        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.getCount(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                txtTongtinmuathue.setText(result + " tin");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    public void getCountTinMua(){
        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query  = "";
        if(idspUser.equals("1")){
            query = "SELECT COUNT(idUser) AS soluong FROM tbl_muathue where trangthai = 2";
        } else {
            query = "SELECT COUNT(idUser) AS soluong FROM tbl_muathue where trangthai = 2 and idUser = "+idUser+"";
        }
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.getCount(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                tindangmua.setText(result + " tin");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public void Tongtien(){

        //SELECT Month(ngaygiaodich) as thoigian, sum(sotien) as tongtien FROM taichinh WHERE DateDiff(Date(CURRENT_DATE) , Date(ngaygiaodich))  <= 30 AND loaigiaodich = 1 and trangthai = 2 AND Year(ngaygiaodich)= YEAR(CURRENT_DATE) GROUP BY Month(ngaygiaodich);

        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query = "";
        if(idspUser.equals("1")){
            query = "SELECT Month(ngaygiaodich) as thoigian, sum(sotien) as tongtien FROM taichinh WHERE loaigiaodich = 2 and trangthai = 2 AND Year(ngaygiaodich)= YEAR(CURRENT_DATE) GROUP BY Month(ngaygiaodich)";
        } else {
            query = "SELECT Month(ngaygiaodich) as thoigian, sum(sotien) as tongtien FROM taichinh WHERE loaigiaodich = 2 and trangthai = 2 AND Year(ngaygiaodich)= YEAR(CURRENT_DATE) GROUP BY Month(ngaygiaodich) and idUser = "+idUser+"";
        }
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.getMoney(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                tongtien.setText(result + " Đồng");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void TienTru(){
        String idspUser = sharedPreferences.getString("idspUser","");
        String idUser = sharedPreferences.getString("idUser","");
        String query = "";
        if(idspUser.equals("1")){
            query = "SELECT Month(ngaygiaodich) as thoigian, sum(sotien) as tongtien FROM taichinh WHERE loaigiaodich = 1 and trangthai = 2 AND Year(ngaygiaodich)= YEAR(CURRENT_DATE) GROUP BY Month(ngaygiaodich)";
        } else {
            query = "SELECT Month(ngaygiaodich) as thoigian, sum(sotien) as tongtien FROM taichinh WHERE loaigiaodich = 1 and trangthai = 2 AND Year(ngaygiaodich)= YEAR(CURRENT_DATE) GROUP BY Month(ngaygiaodich) and idUser = "+idUser+"";
        }
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.getMoney(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                tientru.setText(result + " Đồng");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}