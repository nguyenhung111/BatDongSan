package com.example.btngsn.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btngsn.Model.Taichinh;
import com.example.btngsn.R;

import java.util.ArrayList;

public class AdapterTaichinh extends RecyclerView.Adapter<AdapterTaichinh.ItemHoler> {
    private Context context;
    ArrayList<Taichinh> arrayList;

    public AdapterTaichinh(Context context, ArrayList<Taichinh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdapterTaichinh.ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.viewlichsugiaodich, null);
        AdapterTaichinh.ItemHoler itemHoler = new AdapterTaichinh.ItemHoler(v);
        return itemHoler;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterTaichinh.ItemHoler holder, int position) {

        final  Taichinh taichinh = arrayList.get(position);
        holder.ngaygiaodich.setText(taichinh.getNgaygiaodich());
        String loai = taichinh.getLoaigiaodich();
        if(loai.equals("1")){
           holder.loaigiaodich.setText("Đăng tin");
           holder.sotien.setText("-" + taichinh.getSotien());
           holder.noidung.setText("Trừ tiền đăng tin của tài khoản");
        } else {
            holder.loaigiaodich.setText("Nạp tiền");
            holder.sotien.setText("+" + taichinh.getSotien());
            holder.noidung.setText("Nạp tiền vào tài khoản");
        }
    }

    @Override
    public int getItemCount() {
        if(arrayList != null) {
            return arrayList.size();
        }
        return  0;
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        private TextView ngaygiaodich,loaigiaodich,sotien,noidung;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);

            ngaygiaodich = itemView.findViewById(R.id.ngaygiaodich);
            loaigiaodich = itemView.findViewById(R.id.loaigiaodich);
            sotien = itemView.findViewById(R.id.sotien);
            noidung = itemView.findViewById(R.id.noidung);
        }
    }
}

