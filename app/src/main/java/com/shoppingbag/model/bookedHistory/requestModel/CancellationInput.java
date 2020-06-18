package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class CancellationInput {

    @SerializedName("CancelType")
    private String cancelType;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getAirlinePNR() {
        return airlinePNR;
    }

    public void setAirlinePNR(String airlinePNR) {
        this.airlinePNR = airlinePNR;
    }

    public String getHermesPNR() {
        return hermesPNR;
    }

    public void setHermesPNR(String hermesPNR) {
        this.hermesPNR = hermesPNR;
    }

    @Override
    public String toString() {
        return
                "CancellationInput{" +
                        "cancelType = '" + cancelType + '\'' +
                        ",airlinePNR = '" + airlinePNR + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        "}";
    }
}