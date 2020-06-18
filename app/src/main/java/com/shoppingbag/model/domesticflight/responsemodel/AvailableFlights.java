package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AvailableFlights implements Serializable {

    @SerializedName("OngoingFlights")
    private List<OngoingFlightsItem> ongoingFlights;

    @SerializedName("ReturnFlights")
    private List<ReturnFlightsItem> returnFlights;

    public List<OngoingFlightsItem> getOngoingFlights() {
        return ongoingFlights;
    }

    public void setOngoingFlights(List<OngoingFlightsItem> ongoingFlights) {
        this.ongoingFlights = ongoingFlights;
    }

    public List<ReturnFlightsItem> getReturnFlights() {
        return returnFlights;
    }

    public void setReturnFlights(List<ReturnFlightsItem> returnFlights) {
        this.returnFlights = returnFlights;
    }

    @Override
    public String toString() {
        return
                "AvailableFlights{" +
                        "ongoingFlights = '" + ongoingFlights + '\'' +
                        ",returnFlights = '" + returnFlights + '\'' +
                        "}";
    }
}