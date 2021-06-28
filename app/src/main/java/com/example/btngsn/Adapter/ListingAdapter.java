package com.example.btngsn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Model.CheckConnection;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ItemHoler> {
    Context context;
    ArrayList<Listing> arrayList;

    public ListingAdapter(Context context, ArrayList<Listing> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterviewhome, null);
        ItemHoler itemHoler = new ItemHoler(v);
        return itemHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {

        final Listing listing = arrayList.get(position);

        holder.title.setText(listing.getTitle());
        holder.phone.setText("0"+listing.getPhoneContact());
        holder.price.setText("Giá : "+ listing.getPrice() +listing.getUnit());
        holder.acreage.setText("Diện tích :  " + listing.getAcreage() + " m2");
        holder.address.setText(listing.getAddress());
        holder.dateStart.setText(listing.getDateStart());
        String image = listing.getImage();
        image = image.substring(image.lastIndexOf("/"));
        Glide.with(context).load(APIUtils.Base_Url+"/image/"+image).centerCrop().placeholder(R.drawable.ic_baseline_hide_image_24)
                .error(R.drawable.ic_baseline_error_24).into(holder.imageView);
        String loaitin = listing.getLoaitin();

        if(Integer.parseInt(loaitin) == 2){
            holder.loaitin.setText("Nổi bật");
        } else  if(Integer.parseInt(loaitin) == 3){
            holder.loaitin.setText("Đặc biệt");
        } else {
            holder.loaitin.setVisibility(View.GONE);
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
        private ImageView imageView;
        private TextView title, price, acreage, address, dateStart, phone,loaitin;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.tittle);
            price = (TextView) itemView.findViewById(R.id.price);
            acreage = (TextView) itemView.findViewById(R.id.acreage);
            address = (TextView) itemView.findViewById(R.id.address);
            dateStart = (TextView) itemView.findViewById(R.id.datepost);
            phone = (TextView) itemView.findViewById(R.id.phone);
            loaitin = (TextView) itemView.findViewById(R.id.loaitin);
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
