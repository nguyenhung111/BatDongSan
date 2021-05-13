package com.example.btngsn.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.btngsn.Model.viewSpecies;
import com.example.btngsn.R;

import java.util.ArrayList;

public class speciesAdapter  extends BaseAdapter {
    Activity context;
    ArrayList<viewSpecies> arrayList;

    public speciesAdapter(Activity context, ArrayList<viewSpecies> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.viewspinner, null);

        TextView textname = (TextView) row.findViewById(R.id.txtName);

        final viewSpecies pb = arrayList.get(position);
        textname.setText(pb.getNameSpecies());
        return row;
    }
}
