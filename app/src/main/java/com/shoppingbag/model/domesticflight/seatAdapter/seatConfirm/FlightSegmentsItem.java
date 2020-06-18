package com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightSegmentsItem {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("FlightNumber")
    private String flightNumber;

    @SerializedName("DepartureDate")
    private String departureDate;

    @SerializedName("PassengerDetails")
    private List<PassengerDetailsItem> passengerDetails;

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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
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

    public List<PassengerDetailsItem> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(List<PassengerDetailsItem> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

    @Override
    public String toString() {
        return
                "FlightSegmentsItem{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",flightNumber = '" + flightNumber + '\'' +
                        ",departureDate = '" + departureDate + '\'' +
                        ",passengerDetails = '" + passengerDetails + '\'' +
                        "}";
    }
}