package com.example.btngsn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btngsn.Activity.Detail_Buy_Rent;
import com.example.btngsn.Model.BuyRent;
import com.example.btngsn.Model.CheckConnection;
import com.example.btngsn.R;

import java.util.ArrayList;

public class BuyRentAdapter extends RecyclerView.Adapter<BuyRentAdapter.ItemHoler> {
    Context context;
    ArrayList<BuyRent> arrayList;

    public BuyRentAdapter(Context context, ArrayList<BuyRent> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public BuyRentAdapter.ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterviewmua_thue, null);
        ItemHoler itemHoler = new ItemHoler(v);
        return itemHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {

        final BuyRent listing = arrayList.get(position);

        holder.title.setText(listing.getTieude());
        holder.noidung.setText(listing.getNoidung());
        holder.price.setText("Giá :" + listing.getGia() + " " + listing.getDonvi());
        holder.acreage.setText("Diện tích :" + listing.getDientich());
        holder.address.setText(listing.getDiachi());
        holder.dateStart.setText(listing.getDateStart());
        Glide.with(context).load(listing.getHinhanh()).centerCrop().placeholder(R.drawable.ic_baseline_error_24)
                .error(R.drawable.ic_baseline_hide_image_24).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if(arrayList != null) {
            return arrayList.size();
        }
        return  0;
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, price, acreage, address, dateStart, noidung;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            acreage = (TextView) itemView.findViewById(R.id.acreage);
            address = (TextView) itemView.findViewById(R.id.address);
            dateStart = (TextView) itemView.findViewById(R.id.datepost);
            noidung = (TextView) itemView.findViewById(R.id.noidung);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Detail_Buy_Rent.class);
                    intent.putExtra("thongtinchitiet", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTieude());
                    context.startActivity(intent);
                }
            });
        }
    }
}
