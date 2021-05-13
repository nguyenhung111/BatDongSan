package com.example.btngsn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class viewForm {
    @SerializedName("idForm")
    @Expose
    private String idForm;
    @SerializedName("nameForm")
    @Expose
    private String nameForm;

    public viewForm(String idForm, String nameForm) {
        this.idForm = idForm;
        this.nameForm = nameForm;
    }

    public String getIdForm() {
        return idForm;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    public String getNameForm() {
        return nameForm;
    }

    public void setNameForm(String nameForm) {
        this.nameForm = nameForm;
    }
}
