package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFlightBooking {

    @SerializedName("Response")
    private String response;

    @SerializedName("GetBookingHistorydetail")
    private List<GetBookingHistorydetailItem> getBookingHistorydetail;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<GetBookingHistorydetailItem> getGetBookingHistorydetail() {
        return getBookingHistorydetail;
    }

    public void setGetBookingHistorydetail(List<GetBookingHistorydetailItem> getBookingHistorydetail) {
        this.getBookingHistorydetail = getBookingHistorydetail;
    }

    @Override
    public String toString() {
        return
                "ResponseFlightBooking{" +
                        "response = '" + response + '\'' +
                        ",getBookingHistorydetail = '" + getBookingHistorydetail + '\'' +
                        "}";
    }
}