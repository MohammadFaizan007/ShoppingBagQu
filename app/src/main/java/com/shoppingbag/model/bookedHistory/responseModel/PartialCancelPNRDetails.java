package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartialCancelPNRDetails {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("SupplierId")
    private String supplierId;

    @SerializedName("CancelPassengerDetails")
    private List<CancelPassengerDetailsItem> cancelPassengerDetails;

    @SerializedName("AilinePNR")
    private String ailinePNR;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    @SerializedName("CRSPNR")
    private String cRSPNR;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public List<CancelPassengerDetailsItem> getCancelPassengerDetails() {
        return cancelPassengerDetails;
    }

    public void setCancelPassengerDetails(List<CancelPassengerDetailsItem> cancelPassengerDetails) {
        this.cancelPassengerDetails = cancelPassengerDetails;
    }

    public String getAilinePNR() {
        return ailinePNR;
    }

    public void setAilinePNR(String ailinePNR) {
        this.ailinePNR = ailinePNR;
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
                "PartialCancelPNRDetails{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",supplierId = '" + supplierId + '\'' +
                        ",cancelPassengerDetails = '" + cancelPassengerDetails + '\'' +
                        ",ailinePNR = '" + ailinePNR + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        ",cRSPNR = '" + cRSPNR + '\'' +
                        "}";
    }
}