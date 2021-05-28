package com.example.btngsn.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User  implements Parcelable {

    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("passWord")
    @Expose
    private String passWord;
    @SerializedName("numberPhone")
    @Expose
    private String numberPhone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("idspUser")
    @Expose
    private String idspUser;
    @SerializedName("CMND")
    @Expose
    private String cmnd;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("linkFace")
    @Expose
    private String linkFace;
    @SerializedName("linkWeb")
    @Expose
    private String linkWeb;
    @SerializedName("sodu")
    @Expose
    private String sodu;
    @SerializedName("sex")
    @Expose
    private String sex;

    protected User(Parcel in) {
        idUser = in.readString();
        fullName = in.readString();
        userName = in.readString();
        passWord = in.readString();
        numberPhone = in.readString();
        email = in.readString();
        idspUser = in.readString();
        cmnd = in.readString();
        address = in.readString();
        linkFace = in.readString();
        linkWeb = in.readString();
        sodu = in.readString();
        sex = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdspUser() {
        return idspUser;
    }

    public void setIdspUser(String idspUser) {
        this.idspUser = idspUser;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkFace() {
        return linkFace;
    }

    public void setLinkFace(String linkFace) {
        this.linkFace = linkFace;
    }

    public String getLinkWeb() {
        return linkWeb;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }

    public String getSodu() {
        return sodu;
    }

    public void setSodu(String sodu) {
        this.sodu = sodu;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idUser);
        parcel.writeString(fullName);
        parcel.writeString(userName);
        parcel.writeString(passWord);
        parcel.writeString(numberPhone);
        parcel.writeString(email);
        parcel.writeString(idspUser);
        parcel.writeString(cmnd);
        parcel.writeString(address);
        parcel.writeString(linkFace);
        parcel.writeString(linkWeb);
        parcel.writeString(sodu);
        parcel.writeString(sex);
    }
}