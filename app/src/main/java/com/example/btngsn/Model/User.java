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

    protected User(Parcel in) {
        idUser = in.readString();
        fullName = in.readString();
        userName = in.readString();
        passWord = in.readString();
        numberPhone = in.readString();
        email = in.readString();
        idspUser = in.readString();
        cmnd = in.readString();
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
    }
}