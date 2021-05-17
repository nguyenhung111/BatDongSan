package com.example.btngsn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Model.CheckConnection;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
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
        String localhost = "http://192.168.1.12/batdongsan/image/";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        holder.title.setMaxLines(3);
        holder.title.setEllipsize(TextUtils.TruncateAt.END);
        holder.title.setText(listing.getTitle());
        holder.phone.setText("0" + listing.getPhoneContact());
        holder.price.setText("Giá " + listing.getPrice() + "triệu / m2");
        holder.acreage.setText(listing.getAcreage() + "m2");
        holder.address.setMaxLines(2);
        holder.address.setEllipsize(TextUtils.TruncateAt.END);
        holder.address.setText(listing.getAddress());
//        String url = arrayList.get(0).getImage();
//        String[] link = url.split("/");
//        for (String t : link)
//            String.valueOf(t);
//        String anh = link[5];
//
//        Picasso.get().load(localhost + anh).into(holder.imageView);
        Picasso.get().load(listing.getImage()).into(holder.imageView);
        holder.dateStart.setText(listing.getDateStart());

//        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(listing.image,0,listing.image.length);
//        holder.imageView.setImageBitmap(bmHinhDaiDien);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, price, acreage, address, dateStart, phone;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.tittle);
            price = (TextView) itemView.findViewById(R.id.price);
            acreage = (TextView) itemView.findViewById(R.id.acreage);
            address = (TextView) itemView.findViewById(R.id.address);
            dateStart = (TextView) itemView.findViewById(R.id.datepost);
            phone = (TextView) itemView.findViewById(R.id.phone);
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
