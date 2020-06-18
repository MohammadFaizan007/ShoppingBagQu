package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllAirLinesCodes {

    @SerializedName("airline_code")
    private List<AirlineCodeItem> airlineCode;

    public List<AirlineCodeItem> getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(List<AirlineCodeItem> airlineCode) {
        this.airlineCode = airlineCode;
    }

    @Override
    public String toString() {
        return
                "AllAirLinesCodes{" +
                        "airline_code = '" + airlineCode + '\'' +
                        "}";
    }
}