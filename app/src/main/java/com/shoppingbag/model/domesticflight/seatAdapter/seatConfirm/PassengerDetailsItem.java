package com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm;

import com.google.gson.annotations.SerializedName;

public class PassengerDetailsItem {

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Title")
    private String title;

    @SerializedName("PaxNumber")
    private String paxNumber;

    @SerializedName("PaxType")
    private String paxType;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("SeatNumber")
    private String seatNumber;

    @SerializedName("SeatCode")
    private String seatCode;

    @SerializedName("SeatAmount")
    private int seatAmount;

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

    public String getPaxNumber() {
        return paxNumber;
    }

    public void setPaxNumber(String paxNumber) {
        this.paxNumber = paxNumber;
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

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getSeatAmount() {
        return seatAmount;
    }

    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;
    }

    @Override
    public String toString() {
        return
                "PassengerDetailsItem{" +
                        "firstName = '" + firstName + '\'' +
                        ",title = '" + title + '\'' +
                        ",paxNumber  = '" + paxNumber + '\'' +
                        ",paxType = '" + paxType + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",seatNumber = '" + seatNumber + '\'' +
                        ",seatCode = '" + seatCode + '\'' +
                        ",seatAmount = '" + seatAmount + '\'' +
                        "}";
    }
}