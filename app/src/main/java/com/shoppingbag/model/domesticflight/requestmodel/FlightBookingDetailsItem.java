package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightBookingDetailsItem {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("TourCode")
    private String tourCode;

    @SerializedName("PaymentDetails")
    private PaymentDetails paymentDetails;

    @SerializedName("PassengerDetails")
    private List<PassengerDetailsItem> passengerDetails;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getTourCode() {
        return tourCode;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public List<PassengerDetailsItem> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(List<PassengerDetailsItem> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

    @Override
    public String toString() {
        return
                "FlightBookingDetailsItem{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",tourCode = '" + tourCode + '\'' +
                        ",paymentDetails = '" + paymentDetails + '\'' +
                        ",passengerDetails = '" + passengerDetails + '\'' +
                        "}";
    }
}