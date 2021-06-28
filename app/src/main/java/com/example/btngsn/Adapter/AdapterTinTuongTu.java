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
import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Model.BuyRent;
import com.example.btngsn.Model.CheckConnection;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;

import java.util.ArrayList;

public class AdapterTinTuongTu extends RecyclerView.Adapter<AdapterTinTuongTu.ItemHoler> {
    Context context;
    ArrayList<Listing> arrayList;

    public AdapterTinTuongTu(Context context, ArrayList<Listing> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdapterTinTuongTu.ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.viewtintuongtu, null);
        AdapterTinTuongTu.ItemHoler itemHoler = new AdapterTinTuongTu.ItemHoler(v);
        return itemHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTinTuongTu.ItemHoler holder, int position) {

        final Listing listing = arrayList.get(position);

        holder.price.setText(listing.getPrice() +" "+ listing.getUnit());
        holder.acreage.setText(listing.getAcreage() + " M2");
        holder.address.setText(listing.getAddress());
        Glide.with(context).load(listing.getImage()).centerCrop().placeholder(R.drawable.ic_baseline_error_24)
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
        private TextView  price, acreage, address;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            price = (TextView) itemView.findViewById(R.id.giatien);
            acreage = (TextView) itemView.findViewById(R.id.dientich);
            address = (TextView) itemView.findViewById(R.id.diachi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("thongtinchitiet", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
}
