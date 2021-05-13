package com.example.btngsn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class viewSpecies {
    @SerializedName("idSpecies")
    @Expose
    private String idSpecies;
    @SerializedName("nameSpecies")
    @Expose
    private String nameSpecies;

    public viewSpecies(String idSpecies, String nameSpecies) {
        this.idSpecies = idSpecies;
        this.nameSpecies = nameSpecies;
    }

    public String getIdSpecies() {
        return idSpecies;
    }

    public void setIdSpecies(String idSpecies) {
        this.idSpecies = idSpecies;
    }

    public String getNameSpecies() {
        return nameSpecies;
    }

    public void setNameSpecies(String nameSpecies) {
        this.nameSpecies = nameSpecies;
    }

}
