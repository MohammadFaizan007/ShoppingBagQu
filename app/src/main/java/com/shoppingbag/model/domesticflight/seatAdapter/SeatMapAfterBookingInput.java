package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

public class SeatMapAfterBookingInput {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
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
                "SeatMapAfterBookingInput{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        "}";
    }
}