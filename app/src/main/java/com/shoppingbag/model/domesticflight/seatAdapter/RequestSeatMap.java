package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

import com.shoppingbag.model.domesticflight.requestmodel.Authentication;

public class RequestSeatMap {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("SeatMapAfterBookingInput")
    private SeatMapAfterBookingInput seatMapAfterBookingInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public SeatMapAfterBookingInput getSeatMapAfterBookingInput() {
        return seatMapAfterBookingInput;
    }

    public void setSeatMapAfterBookingInput(SeatMapAfterBookingInput seatMapAfterBookingInput) {
        this.seatMapAfterBookingInput = seatMapAfterBookingInput;
    }

    @Override
    public String toString() {
        return
                "RequestSeatMap{" +
                        ",authentication = '" + authentication + '\'' +
                        "seatMapAfterBookingInput = '" + seatMapAfterBookingInput + '\'' +
                        "}";
    }
}