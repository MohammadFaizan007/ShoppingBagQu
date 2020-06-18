package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancelPassengerDetailsItem {

    @SerializedName("PassengerType")
    private String passengerType;

    @SerializedName("CancelTicketDetails")
    private List<CancelTicketDetailsItem> cancelTicketDetails;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("PaxNumber")
    private int paxNumber;

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public List<CancelTicketDetailsItem> getCancelTicketDetails() {
        return cancelTicketDetails;
    }

    public void setCancelTicketDetails(List<CancelTicketDetailsItem> cancelTicketDetails) {
        this.cancelTicketDetails = cancelTicketDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPaxNumber() {
        return paxNumber;
    }

    public void setPaxNumber(int paxNumber) {
        this.paxNumber = paxNumber;
    }

    @Override
    public String toString() {
        return
                "CancelPassengerDetailsItem{" +
                        "passengerType = '" + passengerType + '\'' +
                        ",cancelTicketDetails = '" + cancelTicketDetails + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",paxNumber = '" + paxNumber + '\'' +
                        "}";
    }
}