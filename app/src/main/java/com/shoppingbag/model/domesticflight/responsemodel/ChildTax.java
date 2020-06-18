package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ChildTax {

    @SerializedName("FareBreakUpDetails")
    private FareBreakUpDetails fareBreakUpDetails;

    public FareBreakUpDetails getFareBreakUpDetails() {
        return fareBreakUpDetails;
    }

    public void setFareBreakUpDetails(FareBreakUpDetails fareBreakUpDetails) {
        this.fareBreakUpDetails = fareBreakUpDetails;
    }

    @Override
    public String toString() {
        return
                "ChildTax{" +
                        "fareBreakUpDetails = '" + fareBreakUpDetails + '\'' +
                        "}";
    }
}