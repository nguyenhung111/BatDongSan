package com.example.btngsn.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyRent  implements Parcelable  {
    @SerializedName("idMuathue")
    @Expose
    private String idMuathue;
    @SerializedName("tieude")
    @Expose
    private String tieude;
    @SerializedName("noidung")
    @Expose
    private String noidung;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("hinhthuc")
    @Expose
    private String hinhthuc;
    @SerializedName("loaidat")
    @Expose
    private String loaidat;
    @SerializedName("diachi")
    @Expose
    private String diachi;
    @SerializedName("dientich")
    @Expose
    private String dientich;
    @SerializedName("gia")
    @Expose
    private String gia;
    @SerializedName("tenlienhe")
    @Expose
    private String tenlienhe;
    @SerializedName("diachilienhe")
    @Expose
    private String diachilienhe;
    @SerializedName("dienthoai")
    @Expose
    private String dienthoai;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("dateStart")
    @Expose
    private String dateStart;
    @SerializedName("dateEnd")
    @Expose
    private String dateEnd;
    @SerializedName("trangthai")
    @Expose
    private String trangthai;

    protected BuyRent(Parcel in) {
        idMuathue = in.readString();
        tieude = in.readString();
        noidung = in.readString();
        hinhanh = in.readString();
        hinhthuc = in.readString();
        loaidat = in.readString();
        diachi = in.readString();
        dientich = in.readString();
        gia = in.readString();
        tenlienhe = in.readString();
        diachilienhe = in.readString();
        dienthoai = in.readString();
        email = in.readString();
        idUser = in.readString();
        dateStart = in.readString();
        dateEnd = in.readString();
        trangthai = in.readString();
    }

    public static final Creator<BuyRent> CREATOR = new Creator<BuyRent>() {
        @Override
        public BuyRent createFromParcel(Parcel in) {
            return new BuyRent(in);
        }

        @Override
        public BuyRent[] newArray(int size) {
            return new BuyRent[size];
        }
    };

    public String getIdMuathue() {
        return idMuathue;
    }

    public void setIdMuathue(String idMuathue) {
        this.idMuathue = idMuathue;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getHinhthuc() {
        return hinhthuc;
    }

    public void setHinhthuc(String hinhthuc) {
        this.hinhthuc = hinhthuc;
    }

    public String getLoaidat() {
        return loaidat;
    }

    public void setLoaidat(String loaidat) {
        this.loaidat = loaidat;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTenlienhe() {
        return tenlienhe;
    }

    public void setTenlienhe(String tenlienhe) {
        this.tenlienhe = tenlienhe;
    }

    public String getDiachilienhe() {
        return diachilienhe;
    }

    public void setDiachilienhe(String diachilienhe) {
        this.diachilienhe = diachilienhe;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idMuathue);
        parcel.writeString(tieude);
        parcel.writeString(noidung);
        parcel.writeString(hinhanh);
        parcel.writeString(hinhthuc);
        parcel.writeString(loaidat);
        parcel.writeString(diachi);
        parcel.writeString(dientich);
        parcel.writeString(gia);
        parcel.writeString(tenlienhe);
        parcel.writeString(diachilienhe);
        parcel.writeString(dienthoai);
        parcel.writeString(email);
        parcel.writeString(idUser);
        parcel.writeString(dateStart);
        parcel.writeString(dateEnd);
        parcel.writeString(trangthai);
    }
}
