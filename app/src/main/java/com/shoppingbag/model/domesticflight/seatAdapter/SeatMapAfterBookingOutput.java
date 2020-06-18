package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatMapAfterBookingOutput {

    @SerializedName("PassengerDetails")
    private List<PassengerDetailsItem> passengerDetails;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

    @SerializedName("FlightSegments")
    private List<FlightSegmentsItem> flightSegments;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    public List<PassengerDetailsItem> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(List<PassengerDetailsItem> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

    public String getAirlinePNR() {
        return airlinePNR;
    }

    public void setAirlinePNR(String airlinePNR) {
        this.airlinePNR = airlinePNR;
    }

    public List<FlightSegmentsItem> getFlightSegments() {
        return flightSegments;
    }

    public void setFlightSegments(List<FlightSegmentsItem> flightSegments) {
        this.flightSegments = flightSegments;
    }

    public String getHermesPNR() {
        return hermesPNR;
    }

    public void setHermesPNR(String hermesPNR) {
        this.hermesPNR = hermesPNR;
    }

    @Override
    public String toString() {
        return
                "SeatMapAfterBookingOutput{" +
                        "passengerDetails = '" + passengerDetails + '\'' +
                        ",airlinePNR = '" + airlinePNR + '\'' +
                        ",flightSegments = '" + flightSegments + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        "}";
    }
}