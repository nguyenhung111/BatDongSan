package com.example.btngsn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
                    txtKhoanggia.setText("T???t c???");
                    gia1 = 0;
                    gia2 = 0;
                    donvi ="";
                } else if (currenProgess == 10) {
                    txtKhoanggia.setText("Th???a thu???n");
                    gia1 = 0;
                    gia2 = 0;
                    donvi = "Th???a thu???n";
                } else if (currenProgess == 20) {
                    txtKhoanggia.setText("< 500 tri???u");
                    gia1 = 1;
                    gia2 = 500;
                    donvi = "Tri???u";
                } else if (currenProgess == 30) {
                    txtKhoanggia.setText("500 - 800 tri???u");
                    gia1 = 500;
                    gia2 = 800;
                    donvi = "Tri???u";
                } else if (currenProgess == 40) {
                    txtKhoanggia.setText("800 tri???u - 1 t???");
                    gia1 = 800;
                    gia2 = 1000;
                    donvi = "Tri???u";
                } else if (currenProgess == 50) {
                    txtKhoanggia.setText("1 t??? - 2 t???");
                    gia1 = 1;
                    gia2 = 2;
                    donvi = "T???";
                } else if (currenProgess == 60) {
                    txtKhoanggia.setText("2 t??? - 3 t???");
                    gia1 = 2;
                    gia2 = 3;
                    donvi = "T???";
                } else if (currenProgess == 70) {
                    txtKhoanggia.setText("3 t??? - 5 t???");
                    gia1 = 3;
                    gia2 = 5;
                    donvi = "T???";
                } else if (currenProgess == 80) {
                    txtKhoanggia.setText("5 t??? - 7 t???");
                    gia1 = 5;
                    gia2 = 7;
                    donvi = "T???";
                } else if (currenProgess == 90) {
                    txtKhoanggia.setText("7 t??? - 10 t???");
                    gia1 = 7;
                    gia2 = 10;
                    donvi = "T???";
                } else if (currenProgess == 100) {
                    txtKhoanggia.setText("10 t??? - 20 t???");
                    gia1 = 10;
                    gia2 = 20;
                    donvi = "T???";
                } else if (currenProgess == 110) {
                    txtKhoanggia.setText("20 t??? - 30 t???");
                    gia1 = 20;
                    gia2 = 30;
                    donvi = "T???";
                } else if (currenProgess == 120) {
                    txtKhoanggia.setText("> 30 t???");
                    gia1 = 30;
                    gia2= 100000;
                    donvi = "T???";
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
                    txtDientich.setText("T???t c???");
                    dientich1 = 0;
                    dientich2 = 0;
                } else if (currenProgess == 10) {
                    txtDientich.setText("<= 30 m2");
                    dientich1 = 1;
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
                    dientich2 = 10000;
                    dientich1 = 300;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("dientich", String.valueOf(dientich1));
                if (currenProgess == 0) {
                    txtDientich.setText("T???t c???");
                    dientich1 = 0;
                    dientich2 = 0;
                } else if (currenProgess == 10) {
                    dientich1 = 1;
                    dientich2 = 30;
                } else if (currenProgess == 20) {
                    dientich1 = 30;
                    dientich2 = 50;
                } else if (currenProgess == 30) {
                    dientich1 = 30;
                    dientich2 = 80;
                } else if (currenProgess == 40) {
                    dientich1 = 80;
                    dientich2 = 100;
                } else if (currenProgess == 50) {
                    dientich1 = 100;
                    dientich2 = 150;
                } else if (currenProgess == 60) {
                    dientich1 = 150;
                    dientich2 = 200;
                } else if (currenProgess == 70) {
                    dientich1 = 200;
                    dientich2 = 250;
                } else if (currenProgess == 80) {
                    dientich1 = 250;
                    dientich2 = 300;
                } else if (currenProgess == 90) {
                    dientich2 = 10000;
                    dientich1 = 300;
                }
            }
        });

        SKsophong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currenProgess = i * 10;
                if (currenProgess == 0) {
                    txtSophong.setText("T???t c???");
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
                txtKhoanggia.setText("T???t c???");
                gia1=0;
                gia2=0;
                SKdientich.setProgress(0);
                txtDientich.setText("T???t c???");
                dientich1=0;
                dientich2=0;
                SKsophong.setProgress(0);
                txtSophong.setText("T???t c???");
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
        String[] hinhthuc = new String[]{"T???t c???", "Nh?? ?????t b??n", "Nh?? ?????t cho thu??"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_select_item, hinhthuc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnForm.setAdapter(arrayAdapter);


        String[] huongnha = new String[]{"T???t c???", "KX??", "????ng", "T??y", "Nam", "B???c", "????ng-B???c", "T??y-B???c", "T??y-Nam", "????ng-Nam"};
        ArrayAdapter<String> arrayhuongnha = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_select_item, huongnha);
        arrayhuongnha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spndirection.setAdapter(arrayhuongnha);

        String[] loaitin = new String[]{"T???t c???", "C??n h??? chung c??", "Nh?? ri??ng", "Nh?? bi???t th???, li???n k???", "Nh?? m???t ph???", "?????t n???n d??? ??n", "B??n ?????t",
                "Trang tr???i,khu ngh??? d?????ng", "Kho,nh?? x?????ng", "Lo???i b???t ?????ng s???n kh??c"};
        ArrayAdapter<String> arrayLoaitin = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_select_item, loaitin);
        arrayLoaitin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSpecies.setAdapter(arrayLoaitin);
    }

    public void sendData() {
        if (spnForm.getSelectedItem().toString().equals("Nh?? ?????t b??n")) {
            hinhthuc = "2";
        } else if (spnForm.getSelectedItem().toString().equals("Nh?? ?????t cho thu??")) {
            hinhthuc = "3";
        } else {
            hinhthuc = "";
        }
        if(spnSpecies.getSelectedItem().toString().equals("T???t c???")){
            loaitin = "";
        }else {
            loaitin = spnSpecies.getSelectedItem().toString();
        }
        if(spndirection.getSelectedItem().toString().equals("T???t c???")){
            huongnha="";
        }else {
            huongnha = spndirection.getSelectedItem().toString();
        }
        Intent intent = new Intent(Search.this, Timkiem.class);
        Bundle bundle = new Bundle();
        if(gia2 == 0  && dientich2 == 0){
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
        else if(gia2 == 0 ){
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
        else  if(dientich2 == 0){
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
        } else {
            bundle.putString("diachi", editSearch.getText().toString());
            bundle.putString("hinhthuc", hinhthuc);
            bundle.putString("loaitin", loaitin);
            bundle.putString("huongnha", huongnha);
            bundle.putString("donvi", donvi);
            bundle.putString("gia1", String.valueOf(gia1));
            bundle.putString("gia2", String.valueOf(gia2));
            bundle.putString("dientich1", String.valueOf(dientich1));
            bundle.putString("dientich2", String.valueOf(dientich2));
            bundle.putString("sophongngu", sophong);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }


}