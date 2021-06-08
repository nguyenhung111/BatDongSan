package com.example.btngsn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("idListing")
    @Expose
    private String idListing;

    public Image(String id, String hinhanh, String idListing) {
        this.id = id;
        this.hinhanh = hinhanh;
        this.idListing = idListing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getIdListing() {
        return idListing;
    }

    public void setIdListing(String idListing) {
        this.idListing = idListing;
    }
}
