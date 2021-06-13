package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.btngsn.Model.viewSpecies;
import com.example.btngsn.R;


import java.util.ArrayList;


public class Search extends AppCompatActivity {
    SeekBar SKkhoanggia, SKdientich, SKsophong;
    EditText editSearch;
    TextView txtKhoanggia, txtDientich, txtSophong, txtBoloc, timkiem;
    private int currentMax = 120, currentStep = 10;
    int currenProgess;


    Spinner spnForm, spnSpecies, spndirection;
    String donvi, hinhthuc, loaitin, huongnha, sophong;
    int gia1, gia2;
    int dientich1, dientich2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        Click();
        Event();
        EventSpiner();
    }

    public void init() {
        SKkhoanggia = (SeekBar) findViewById(R.id.khoanggia);
        SKkhoanggia.setMax(currentMax / currentStep);
        SKkhoanggia.setProgress(0);
        SKdientich = (SeekBar) findViewById(R.id.dientich);
        SKdientich.setMax(90 / 10);
        SKdientich.setProgress(0);
        SKsophong = (SeekBar) findViewById(R.id.sophong);
        SKsophong.setMax(50 / 10);
        SKsophong.setProgress(0);

        txtKhoanggia = (TextView) findViewById(R.id.txtKhoanggia);
        txtDientich = (TextView) findViewById(R.id.txtDientich);
        txtSophong = (TextView) findViewById(R.id.txtSophong);
        txtBoloc = (TextView) findViewById(R.id.txtBoloc);
        timkiem = (TextView) findViewById(R.id.timkiem);
        editSearch = (EditText) findViewById(R.id.editSearch);
        spnForm = (Spinner) findViewById(R.id.spnForm);
        spnSpecies = (Spinner) findViewById(R.id.spnSpecies);
        spndirection = (Spinner) findViewById(R.id.spndirection);
    }

    @SuppressLint("SetTextI18n")
    public void Click() {
        SKkhoanggia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currenProgess = i * 10;
                if (currenProgess == 0) {
                    txtKhoanggia.setText("Tất cả");
                    gia1 = 0;
                    gia2 = 0;
                } else if (currenProgess == 10) {
                    txtKhoanggia.setText("Thỏa thuận");
                    donvi = "Thỏa thuận";
                } else if (currenProgess == 20) {
                    txtKhoanggia.setText("< 500 triệu");
                    gia1 = 1;
                    gia2 = 500;
                    donvi = "Triệu";
                } else if (currenProgess == 30) {
                    txtKhoanggia.setText("500 - 800 triệu");
                    gia1 = 500;
                    gia2 = 800;
                    donvi = "Triệu";
                } else if (currenProgess == 40) {
                    txtKhoanggia.setText("800 triệu - 1 tỷ");
                    gia1 = 800;
                    gia2 = 1000;
                    donvi = "Triệu";
                } else if (currenProgess == 50) {
                    txtKhoanggia.setText("1 tỷ - 2 tỷ");
                    gia1 = 1;
                    gia2 = 2;
                    donvi = "Tỷ";
                } else if (currenProgess == 60) {
                    txtKhoanggia.setText("2 tỷ - 3 tỷ");
                    gia1 = 2;
                    gia2 = 3;
                    donvi = "Tỷ";
                } else if (currenProgess == 70) {
                    txtKhoanggia.setText("3 tỷ - 5 tỷ");
                    gia1 = 3;
                    gia2 = 5;
                    donvi = "Tỷ";
                } else if (currenProgess == 80) {
                    txtKhoanggia.setText("5 tỷ - 7 tỷ");
                    gia1 = 5;
                    gia2 = 7;
                    donvi = "Tỷ";
                } else if (currenProgess == 90) {
                    txtKhoanggia.setText("7 tỷ - 10 tỷ");
                    gia1 = 7;
                    gia2 = 10;
                    donvi = "Tỷ";
                } else if (currenProgess == 100) {
                    txtKhoanggia.setText("10 tỷ - 20 tỷ");
                    gia1 = 10;
                    gia2 = 20;
                    donvi = "Tỷ";
                } else if (currenProgess == 110) {
                    txtKhoanggia.setText("20 tỷ - 30 tỷ");
                    gia1 = 20;
                    gia2 = 30;
                    donvi = "Tỷ";
                } else if (currenProgess == 120) {
                    txtKhoanggia.setText("> 30 tỷ");
                    gia1 = 30;
                    donvi = "Tỷ";
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SKdientich.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currenProgess = i * 10;
                if (currenProgess == 0) {
                    txtDientich.setText("Tất cả");
                    dientich1 = 0;
                    dientich2 = 0;
                } else if (currenProgess == 10) {
                    txtDientich.setText("<= 30 m2>");
                    dientich1 = 0;
                    dientich2 = 0;
                    dientich2 = 30;
                } else if (currenProgess == 20) {
                    txtDientich.setText("30 - 50 m2");
                    dientich1 = 30;
                    dientich2 = 50;
                } else if (currenProgess == 30) {
                    txtDientich.setText("50 -80 m2");
                    dientich1 = 30;
                    dientich2 = 80;
                } else if (currenProgess == 40) {
                    txtDientich.setText("80 - 100 m2");
                    dientich1 = 80;
                    dientich2 = 100;
                } else if (currenProgess == 50) {
                    txtDientich.setText("100 - 150 m2");
                    dientich1 = 100;
                    dientich2 = 150;
                } else if (currenProgess == 60) {
                    txtDientich.setText("150 - 200 m2");
                    dientich1 = 150;
                    dientich2 = 200;
                } else if (currenProgess == 70) {
                    txtDientich.setText("200 - 250 m2");
                    dientich1 = 200;
                    dientich2 = 250;
                } else if (currenProgess == 80) {
                    txtDientich.setText("250-300 m2");
                    dientich1 = 250;
                    dientich2 = 300;
                } else if (currenProgess == 90) {
                    txtDientich.setText(">= 300 m2");
                    dientich2 = 300;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SKsophong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currenProgess = i * 10;
                if (currenProgess == 0) {
                    txtSophong.setText("Tất cả");
                } else if (currenProgess == 10) {
                    txtSophong.setText("1+");
                    sophong = "1";
                } else if (currenProgess == 20) {
                    txtSophong.setText("2+");
                    sophong = "2";
                } else if (currenProgess == 30) {
                    txtSophong.setText("3+");
                    sophong = "3";
                } else if (currenProgess == 40) {
                    txtSophong.setText("4+");
                    sophong = "4";
                } else if (currenProgess == 50) {
                    txtSophong.setText("5+");
                    sophong = "5";
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Event() {
        txtBoloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SKkhoanggia.setProgress(0);
                txtKhoanggia.setText("Tất cả");
                gia1=0;
                gia2=0;
                SKdientich.setProgress(0);
                txtDientich.setText("Tất cả");
                dientich1=0;
                dientich2=0;
                SKsophong.setProgress(0);
                txtSophong.setText("Tất cả");
                sophong="";
            }
        });
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void EventSpiner() {
        String[] hinhthuc = new String[]{"Tất cả", "Nhà đất bán", "Nhà đất cho thuê"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_select_item, hinhthuc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnForm.setAdapter(arrayAdapter);


        String[] huongnha = new String[]{"Tất cả", "KXĐ", "Đông", "Tây", "Nam", "Bắc", "Đông-Bắc", "Tây-Bắc", "Tây-Nam", "Đông-Nam"};
        ArrayAdapter<String> arrayhuongnha = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_select_item, huongnha);
        arrayhuongnha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spndirection.setAdapter(arrayhuongnha);

        String[] loaitin = new String[]{"Tất cả", "Căn hộ chung cư", "Nhà riêng", "Nhà biệt thự, liền kề", "Nhà mặt phố", "Đất nền dự án", "Bán đất",
                "Trang trại,khu nghỉ dưỡng", "Kho,nhà xưởng", "Loại bất động sản khác"};
        ArrayAdapter<String> arrayLoaitin = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_select_item, loaitin);
        arrayLoaitin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSpecies.setAdapter(arrayLoaitin);
    }

    public void sendData() {
        if (spnForm.getSelectedItem().toString().equals("Nhà đất bán")) {
            hinhthuc = "2";
        } else if (spnForm.getSelectedItem().toString().equals("Nhà đất bán")) {
            hinhthuc = "3";
        } else {
            hinhthuc = "";
        }
        if(spnSpecies.getSelectedItem().toString().equals("Tất cả")){
            loaitin = "";
        }else {
            loaitin = spnSpecies.getSelectedItem().toString();
        }
        if(spndirection.getSelectedItem().toString().equals("Tất cả")){
            huongnha="";
        }else {
            huongnha = spndirection.getSelectedItem().toString();
        }
        Intent intent = new Intent(Search.this, Timkiem.class);
        Bundle bundle = new Bundle();
        if(gia1 == 0 && gia2 == 0 && dientich1 == 0 && dientich2 == 0){
            bundle.putString("diachi", editSearch.getText().toString());
            bundle.putString("hinhthuc", hinhthuc);
            bundle.putString("loaitin", loaitin);
            bundle.putString("huongnha", huongnha);
            bundle.putString("donvi", donvi);
            bundle.putString("gia1", "");
            bundle.putString("gia2", "");
            bundle.putString("dientich1", "");
            bundle.putString("dientich2", "");
            bundle.putString("sophongngu", sophong);
        }
        else if(gia1 == 0 && gia2 == 0 ){
            bundle.putString("diachi", editSearch.getText().toString());
            bundle.putString("hinhthuc", hinhthuc);
            bundle.putString("loaitin", loaitin);
            bundle.putString("huongnha", huongnha);
            bundle.putString("donvi", donvi);
            bundle.putString("gia1", "");
            bundle.putString("gia2", "");
            bundle.putString("dientich1", String.valueOf(dientich1));
            bundle.putString("dientich2", String.valueOf(dientich2));
            bundle.putString("sophongngu", sophong);
        }
        else  if( dientich1 == 0 && dientich2 == 0){
            bundle.putString("diachi", editSearch.getText().toString());
            bundle.putString("hinhthuc", hinhthuc);
            bundle.putString("loaitin", loaitin);
            bundle.putString("huongnha", huongnha);
            bundle.putString("donvi", donvi);
            bundle.putString("gia1", String.valueOf(gia1));
            bundle.putString("gia2", String.valueOf(gia2));
            bundle.putString("dientich1", "");
            bundle.putString("dientich2", "");
            bundle.putString("sophongngu", sophong);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }


}