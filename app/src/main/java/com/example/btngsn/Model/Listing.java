
package com.example.btngsn.Model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Listing implements Parcelable {

    @SerializedName("idListing")
    @Expose
    private String idListing;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("idForm")
    @Expose
    private String idForm;
    @SerializedName("idSpecies")
    @Expose
    private String idSpecies;
    @SerializedName("acreage")
    @Expose
    private String acreage;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("floors")
    @Expose
    private String floors;
    @SerializedName("bedroom")
    @Expose
    private String bedroom;
    @SerializedName("toilet")
    @Expose
    private String toilet;
    @SerializedName("idHouse")
    @Expose
    private String idHouse;
    @SerializedName("idBancoly")
    @Expose
    private String idBancoly;
    @SerializedName("furniture")
    @Expose
    private String furniture;
    @SerializedName("juridical")
    @Expose
    private String juridical;
    @SerializedName("nameContact")
    @Expose
    private String nameContact;
    @SerializedName("phoneContact")
    @Expose
    private String phoneContact;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("dateStart")
    @Expose
    private String dateStart;
    @SerializedName("dateEnd")
    @Expose
    private String dateEnd;

    protected Listing(Parcel in) {
        idListing = in.readString();
        title = in.readString();
        idForm = in.readString();
        idSpecies = in.readString();
        acreage = in.readString();
        price = in.readString();
        address = in.readString();
        image = in.readString();
        floors = in.readString();
        bedroom = in.readString();
        toilet = in.readString();
        idHouse = in.readString();
        idBancoly = in.readString();
        furniture = in.readString();
        juridical = in.readString();
        nameContact = in.readString();
        phoneContact = in.readString();
        idUser = in.readString();
        dateStart = in.readString();
        dateEnd = in.readString();
    }

    public static final Creator<Listing> CREATOR = new Creator<Listing>() {
        @Override
        public Listing createFromParcel(Parcel in) {
            return new Listing(in);
        }

        @Override
        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };

    public String getIdListing() {
        return idListing;
    }

    public void setIdListing(String idListing) {
        this.idListing = idListing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdForm() {
        return idForm;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    public String getIdSpecies() {
        return idSpecies;
    }

    public void setIdSpecies(String idSpecies) {
        this.idSpecies = idSpecies;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getIdHouse() {
        return idHouse;
    }

    public void setIdHouse(String idHouse) {
        this.idHouse = idHouse;
    }

    public String getIdBancoly() {
        return idBancoly;
    }

    public void setIdBancoly(String idBancoly) {
        this.idBancoly = idBancoly;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public String getJuridical() {
        return juridical;
    }

    public void setJuridical(String juridical) {
        this.juridical = juridical;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idListing);
        parcel.writeString(title);
        parcel.writeString(idForm);
        parcel.writeString(idSpecies);
        parcel.writeString(acreage);
        parcel.writeString(price);
        parcel.writeString(address);
        parcel.writeString(image);
        parcel.writeString(floors);
        parcel.writeString(bedroom);
        parcel.writeString(toilet);
        parcel.writeString(idHouse);
        parcel.writeString(idBancoly);
        parcel.writeString(furniture);
        parcel.writeString(juridical);
        parcel.writeString(nameContact);
        parcel.writeString(phoneContact);
        parcel.writeString(idUser);
        parcel.writeString(dateStart);
        parcel.writeString(dateEnd);
    }
}