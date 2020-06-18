package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class BookedHistoryInput {

    @SerializedName("TravelType")
    private String travelType;

    @SerializedName("FromDate")
    private String fromDate;

    @SerializedName("ToDate")
    private String toDate;

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return
                "BookedHistoryInput{" +
                        "travelType = '" + travelType + '\'' +
                        ",fromDate = '" + fromDate + '\'' +
                        ",toDate = '" + toDate + '\'' +
                        "}";
    }
}