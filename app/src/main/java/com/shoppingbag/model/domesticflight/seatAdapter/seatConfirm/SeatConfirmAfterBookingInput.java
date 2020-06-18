package com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatConfirmAfterBookingInput {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("AirlinePNR")
    private String airlinePnr;

    @SerializedName("FlightSegments")
    private List<FlightSegmentsItem> flightSegments;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
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
                "SeatConfirmAfterBookingInput{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        "airlinePnr = '" + airlinePnr + '\'' +
                        ",flightSegments = '" + flightSegments + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        "}";
    }

    public String getAirlinePnr() {
        return airlinePnr;
    }

    public void setAirlinePnr(String airlinePnr) {
        this.airlinePnr = airlinePnr;
    }
}