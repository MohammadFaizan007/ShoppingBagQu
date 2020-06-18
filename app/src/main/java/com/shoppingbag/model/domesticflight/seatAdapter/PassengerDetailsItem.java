package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

public class PassengerDetailsItem {

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Title")
    private String title;

    @SerializedName("PaxType")
    private String paxType;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("PaxNumber")
    private String paxNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaxType() {
        return paxType;
    }

    public void setPaxType(String paxType) {
        this.paxType = paxType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPaxNumber() {
        return paxNumber;
    }

    public void setPaxNumber(String paxNumber) {
        this.paxNumber = paxNumber;
    }

    @Override
    public String toString() {
        return
                "PassengerDetailsItem{" +
                        "firstName = '" + firstName + '\'' +
                        ",title = '" + title + '\'' +
                        ",paxType = '" + paxType + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",paxNumber = '" + paxNumber + '\'' +
                        "}";
    }
}