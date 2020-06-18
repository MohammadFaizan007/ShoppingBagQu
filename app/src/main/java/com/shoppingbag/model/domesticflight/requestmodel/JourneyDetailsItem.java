package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JourneyDetailsItem implements Serializable {

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("TravelDate")
    private String travelDate;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    @Override
    public String toString() {
        return
                "JourneyDetailsItem{" +
                        "origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",travelDate = '" + travelDate + '\'' +
                        "}";
    }
}