package com.example.btngsn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {
    @SerializedName("idFavorit")
    @Expose
    private String idFavorit;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idListing")
    @Expose
    private String idListing;

    public String getIdFavorit() {
        return idFavorit;
    }

    public void setIdFavorit(String idFavorit) {
        this.idFavorit = idFavorit;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdListing() {
        return idListing;
    }

    public void setIdListing(String idListing) {
        this.idListing = idListing;
    }

}
