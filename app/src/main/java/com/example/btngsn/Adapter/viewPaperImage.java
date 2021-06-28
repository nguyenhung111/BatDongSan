package com.example.btngsn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.btngsn.Model.Image;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;

import java.util.ArrayList;

public class viewPaperImage extends PagerAdapter {
    ArrayList<Image> arrayList;
    Context context;

    public viewPaperImage(ArrayList<Image> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==  object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewimagedetail, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String image = arrayList.get(position).getHinhanh();
        image = image.substring(image.lastIndexOf("/"));
        Glide.with(context).load(APIUtils.Base_Url+"/image/"+image).centerCrop().into(imageView);

        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
