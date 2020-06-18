package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class TaxReqFlightSegmentsItem {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("SupplierId")
    private String supplierId;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("ETicketFlag")
    private int eTicketFlag;

    @SerializedName("FlightId")
    private String flightId;

    @SerializedName("BasicAmount")
    private int basicAmount;

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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public int getETicketFlag() {
        return eTicketFlag;
    }

    public void setETicketFlag(int eTicketFlag) {
        this.eTicketFlag = eTicketFlag;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public int getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(int basicAmount) {
        this.basicAmount = basicAmount;
    }

    @Override
    public String toString() {
        return
                "TaxReqFlightSegmentsItem{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",supplierId = '" + supplierId + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",eTicketFlag = '" + eTicketFlag + '\'' +
                        ",flightId = '" + flightId + '\'' +
                        ",basicAmount = '" + basicAmount + '\'' +
                        "}";
    }
}