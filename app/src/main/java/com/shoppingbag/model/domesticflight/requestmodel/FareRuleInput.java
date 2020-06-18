package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class FareRuleInput {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("SupplierId")
    private String supplierId;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("FlightId")
    private String flightId;

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

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        return
                "FareRuleInput{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",supplierId = '" + supplierId + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",flightId = '" + flightId + '\'' +
                        "}";
    }
}