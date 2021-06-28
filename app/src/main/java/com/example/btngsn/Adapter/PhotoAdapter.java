package com.example.btngsn.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHoler> {
    private Context context;
    private ArrayList<Uri> uriList;

    public PhotoAdapter(Context context, ArrayList<Uri> uriList) {
        this.context = context;
        this.uriList = uriList;
    }


    @NonNull
    @Override
    public PhotoViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

        return new PhotoViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHoler holder, int position) {
        Glide.with(context)
                .load(uriList.get(position))
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        if(uriList !=null){
            return uriList.size();
        }
        return 0;
    }

    public class PhotoViewHoler extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public PhotoViewHoler(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imd_photo);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                    dialog.setMessage("Bạn muốn xóa ảnh này");
//                    dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            uriList.remove(getPosition());
//                        }
//                    });
//                    dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//
//                        }
//                    });
//                    dialog.show();
//                }
//            });
        }
    }
}