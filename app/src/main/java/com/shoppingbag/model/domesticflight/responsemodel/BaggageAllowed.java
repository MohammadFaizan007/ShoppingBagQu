package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaggageAllowed implements Serializable {

    @SerializedName("CheckInBaggage")
    private String checkInBaggage;

    @SerializedName("HandBaggage")
    private String handBaggage;

    public String getCheckInBaggage() {
        return checkInBaggage;
    }

    public void setCheckInBaggage(String checkInBaggage) {
        this.checkInBaggage = checkInBaggage;
    }

    public String getHandBaggage() {
        return handBaggage;
    }

    public void setHandBaggage(String handBaggage) {
        this.handBaggage = handBaggage;
    }

    @Override
    public String toString() {
        return
                "BaggageAllowed{" +
                        "checkInBaggage = '" + checkInBaggage + '\'' +
                        ",handBaggage = '" + handBaggage + '\'' +
                        "}";
    }
}