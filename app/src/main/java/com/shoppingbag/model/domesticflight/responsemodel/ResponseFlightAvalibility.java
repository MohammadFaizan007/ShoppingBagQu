package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ResponseFlightAvalibility implements Serializable {

    @SerializedName("AvailabilityOutput")
    private AvailabilityOutput availabilityOutput;

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    public AvailabilityOutput getAvailabilityOutput() {
        return availabilityOutput;
    }

    public void setAvailabilityOutput(AvailabilityOutput availabilityOutput) {
        this.availabilityOutput = availabilityOutput;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    @Override
    public String toString() {
        return
                "ResponseFlightAvalibility{" +
                        "availabilityOutput = '" + availabilityOutput + '\'' +
                        ",responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        "}";
    }
}