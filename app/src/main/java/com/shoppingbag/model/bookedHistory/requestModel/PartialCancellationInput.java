package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartialCancellationInput {

    @SerializedName("PartialCancelPassengerDetails")
    private List<PartialCancelPassengerDetailsItem> partialCancelPassengerDetails;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    @SerializedName("CRSPNR")
    private String cRSPNR;

    public List<PartialCancelPassengerDetailsItem> getPartialCancelPassengerDetails() {
        return partialCancelPassengerDetails;
    }

    public void setPartialCancelPassengerDetails(List<PartialCancelPassengerDetailsItem> partialCancelPassengerDetails) {
        this.partialCancelPassengerDetails = partialCancelPassengerDetails;
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

    public String getCRSPNR() {
        return cRSPNR;
    }

    public void setCRSPNR(String cRSPNR) {
        this.cRSPNR = cRSPNR;
    }

    @Override
    public String toString() {
        return
                "PartialCancellationInput{" +
                        "partialCancelPassengerDetails = '" + partialCancelPassengerDetails + '\'' +
                        ",airlinePNR = '" + airlinePNR + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        ",cRSPNR = '" + cRSPNR + '\'' +
                        "}";
    }
}