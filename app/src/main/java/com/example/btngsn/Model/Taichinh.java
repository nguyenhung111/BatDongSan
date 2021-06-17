package com.example.btngsn.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taichinh implements Parcelable {

    @SerializedName("mataichinh")
    @Expose
    private String mataichinh;
    @SerializedName("sotien")
    @Expose
    private String sotien;
    @SerializedName("ngaygiaodich")
    @Expose
    private String ngaygiaodich;
    @SerializedName("loaigiaodich")
    @Expose
    private String loaigiaodich;
    @SerializedName("trangthai")
    @Expose
    private String trangthai;
    @SerializedName("idUser")
    @Expose
    private String idUser;

    protected Taichinh(Parcel in) {
        mataichinh = in.readString();
        sotien = in.readString();
        ngaygiaodich = in.readString();
        loaigiaodich = in.readString();
        trangthai = in.readString();
        idUser = in.readString();
    }

    public static final Creator<Taichinh> CREATOR = new Creator<Taichinh>() {
        @Override
        public Taichinh createFromParcel(Parcel in) {
            return new Taichinh(in);
        }

        @Override
        public Taichinh[] newArray(int size) {
            return new Taichinh[size];
        }
    };

    public String getMataichinh() {
        return mataichinh;
    }

    public void setMataichinh(String mataichinh) {
        this.mataichinh = mataichinh;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

    public String getNgaygiaodich() {
        return ngaygiaodich;
    }

    public void setNgaygiaodich(String ngaygiaodich) {
        this.ngaygiaodich = ngaygiaodich;
    }

    public String getLoaigiaodich() {
        return loaigiaodich;
    }

    public void setLoaigiaodich(String loaigiaodich) {
        this.loaigiaodich = loaigiaodich;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mataichinh);
        parcel.writeString(sotien);
        parcel.writeString(ngaygiaodich);
        parcel.writeString(loaigiaodich);
        parcel.writeString(trangthai);
        parcel.writeString(idUser);
    }
}