package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class RequestFlightBookedHistory {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("BookedHistoryInput")
    private BookedHistoryInput bookedHistoryInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public BookedHistoryInput getBookedHistoryInput() {
        return bookedHistoryInput;
    }

    public void setBookedHistoryInput(BookedHistoryInput bookedHistoryInput) {
        this.bookedHistoryInput = bookedHistoryInput;
    }

    @Override
    public String toString() {
        return
                "RequestFlightBookedHistory{" +
                        "authentication = '" + authentication + '\'' +
                        ",bookedHistoryInput = '" + bookedHistoryInput + '\'' +
                        "}";
    }
}