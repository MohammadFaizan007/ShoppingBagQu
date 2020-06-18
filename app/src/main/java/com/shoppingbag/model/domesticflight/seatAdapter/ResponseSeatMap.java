package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

public class ResponseSeatMap {

    @SerializedName("FailureRemarks")
    private Object failureRemarks;

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("SeatMapAfterBookingOutput")
    private SeatMapAfterBookingOutput seatMapAfterBookingOutput;

    public Object getFailureRemarks() {
        return failureRemarks;
    }

    public void setFailureRemarks(Object failureRemarks) {
        this.failureRemarks = failureRemarks;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public SeatMapAfterBookingOutput getSeatMapAfterBookingOutput() {
        return seatMapAfterBookingOutput;
    }

    public void setSeatMapAfterBookingOutput(SeatMapAfterBookingOutput seatMapAfterBookingOutput) {
        this.seatMapAfterBookingOutput = seatMapAfterBookingOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseSeatMap{" +
                        "failureRemarks = '" + failureRemarks + '\'' +
                        ",responseStatus = '" + responseStatus + '\'' +
                        ",seatMapAfterBookingOutput = '" + seatMapAfterBookingOutput + '\'' +
                        "}";
    }
}