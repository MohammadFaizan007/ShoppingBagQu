package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvailabilityOutput implements Serializable {

    @SerializedName("AvailableFlights")
    private AvailableFlights availableFlights;

    public AvailableFlights getAvailableFlights() {
        return availableFlights;
    }

    public void setAvailableFlights(AvailableFlights availableFlights) {
        this.availableFlights = availableFlights;
    }

    @Override
    public String toString() {
        return
                "AvailabilityOutput{" +
                        "availableFlights = '" + availableFlights + '\'' +
                        "}";
    }
}