package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

public class FlightSegmentsItem {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("AirCraftType")
    private String airCraftType;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("ArrivalDate")
    private String arrivalDate;

    @SerializedName("FlightNumber")
    private String flightNumber;

    @SerializedName("DepartureDate")
    private String departureDate;

    @SerializedName("SeatLayoutDetails")
    private SeatLayoutDetails seatLayoutDetails;

    @SerializedName("ClassType")
    private String classType;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirCraftType() {
        return airCraftType;
    }

    public void setAirCraftType(String airCraftType) {
        this.airCraftType = airCraftType;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public SeatLayoutDetails getSeatLayoutDetails() {
        return seatLayoutDetails;
    }

    public void setSeatLayoutDetails(SeatLayoutDetails seatLayoutDetails) {
        this.seatLayoutDetails = seatLayoutDetails;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return
                "FlightSegmentsItem{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",airCraftType = '" + airCraftType + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",arrivalDate = '" + arrivalDate + '\'' +
                        ",flightNumber = '" + flightNumber + '\'' +
                        ",departureDate = '" + departureDate + '\'' +
                        ",seatLayoutDetails = '" + seatLayoutDetails + '\'' +
                        ",classType = '" + classType + '\'' +
                        "}";
    }
}