package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class BaggagesItem {

    @SerializedName("BaggageCode")
    private String baggageCode;

    @SerializedName("BaggageDescription")
    private String baggageDescription;

    @SerializedName("BaggageAmount")
    private int baggageAmount;

    public String getBaggageCode() {
        return baggageCode;
    }

    public void setBaggageCode(String baggageCode) {
        this.baggageCode = baggageCode;
    }

    public String getBaggageDescription() {
        return baggageDescription;
    }

    public void setBaggageDescription(String baggageDescription) {
        this.baggageDescription = baggageDescription;
    }

    public int getBaggageAmount() {
        return baggageAmount;
    }

    public void setBaggageAmount(int baggageAmount) {
        this.baggageAmount = baggageAmount;
    }

    @Override
    public String toString() {
        return
                "BaggagesItem{" +
                        "baggageCode = '" + baggageCode + '\'' +
                        ",baggageDescription = '" + baggageDescription + '\'' +
                        ",baggageAmount = '" + baggageAmount + '\'' +
                        "}";
    }
}