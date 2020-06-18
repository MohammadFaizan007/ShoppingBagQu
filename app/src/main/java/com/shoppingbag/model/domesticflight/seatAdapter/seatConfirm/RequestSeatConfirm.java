package com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm;

import com.google.gson.annotations.SerializedName;

import com.shoppingbag.model.domesticflight.requestmodel.Authentication;

public class RequestSeatConfirm {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("SeatConfirmAfterBookingInput")
    private SeatConfirmAfterBookingInput seatConfirmAfterBookingInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public SeatConfirmAfterBookingInput getSeatConfirmAfterBookingInput() {
        return seatConfirmAfterBookingInput;
    }

    public void setSeatConfirmAfterBookingInput(SeatConfirmAfterBookingInput seatConfirmAfterBookingInput) {
        this.seatConfirmAfterBookingInput = seatConfirmAfterBookingInput;
    }

    @Override
    public String toString() {
        return
                "RequestSeatConfirm{" +
                        "authentication = '" + authentication + '\'' +
                        ",seatConfirmAfterBookingInput = '" + seatConfirmAfterBookingInput + '\'' +
                        "}";
    }
}