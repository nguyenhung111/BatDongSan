package com.example.btngsn.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.btngsn.Model.DataDistrict;
import com.example.btngsn.R;

import java.util.List;

public  class DistrictSpinnerAdapter implements android.widget.SpinnerAdapter {

    private List<DataDistrict> dataDistrictList;
    Context context;

    public DistrictSpinnerAdapter(List<DataDistrict> dataDistrictList, Context context) {
        this.dataDistrictList = dataDistrictList;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.viewspinner, parent, false);
        TextView tvTitle = convertView.findViewById(R.id.txtName);
        tvTitle.setText(dataDistrictList.get(position).getTitle());

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.viewspinner, parent, false);
        TextView tvTitle = convertView.findViewById(R.id.txtName);
        tvTitle.setText(dataDistrictList.get(position).getTitle());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return dataDistrictList.size();
    }

    @Override
    public DataDistrict getItem(int position) {
        return dataDistrictList.get(position);
    }






    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public int getItemViewType(int position) {
        return 0;
    }



    @Override
    public boolean isEmpty() {
        return false;
    }
}