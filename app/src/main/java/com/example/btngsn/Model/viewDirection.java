package com.example.btngsn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class viewDirection {
    @SerializedName("idHouse")
    @Expose
    private String idHouse;
    @SerializedName("nameDirection")
    @Expose
    private String nameDirection;

    public viewDirection(String idHouse, String nameDirection) {
        this.idHouse = idHouse;
        this.nameDirection = nameDirection;
    }

    public String getIdHouse() {
        return idHouse;
    }

    public void setIdHouse(String idHouse) {
        this.idHouse = idHouse;
    }

    public String getNameDirection() {
        return nameDirection;
    }

    public void setNameDirection(String nameDirection) {
        this.nameDirection = nameDirection;
    }
}
