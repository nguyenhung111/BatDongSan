package com.example.btngsn.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btngsn.R;

import java.io.IOException;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHoler> {
    private Context context;
    private List<Uri> uriList;

    public PhotoAdapter(Context context, List<Uri> uriList) {
        this.context = context;
        this.uriList = uriList;
    }

    public void setData(List<Uri> list){
        this.uriList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

        return new PhotoViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHoler holder, int position) {
        Uri uri = uriList.get(position);
        if(uri == null){
            return;
        }

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            if(bitmap != null){
                holder.imageView.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        }
    }
}
