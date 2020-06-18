package com.shoppingbag.model.domesticflight.responsemodel;

import java.io.Serializable;

public class FlightItem implements Serializable {

    private String FlightId = "";
    private String AirlineCode = "";
    private String FlightNumber = "";
    private String Origin = "";
    private String Destination = "";
    private String DepartureDateTime = "";
    private String ArrivalDateTime = "";
    private String Duration = "";
    private String NumberofStops = "";
    private String GrossAmount = "";
    private String FareType = "";
    private String Via = "";
    private boolean Multiple = false;


    public String getFlightId() {
        return FlightId;
    }

    public void setFlightId(String flightId) {
        FlightId = flightId;
    }

    public String getAirlineCode() {
        return AirlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        AirlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return FlightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        FlightNumber = flightNumber;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDepartureDateTime() {
        return DepartureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        DepartureDateTime = departureDateTime;
    }

    public String getArrivalDateTime() {
        return ArrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        ArrivalDateTime = arrivalDateTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getNumberofStops() {
        return NumberofStops;
    }

    public void setNumberofStops(String numberofStops) {
        NumberofStops = numberofStops;
    }

    public String getGrossAmount() {
        return GrossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        GrossAmount = grossAmount;
    }

    public boolean isMultiple() {
        return Multiple;
    }

    public void setMultiple(boolean multiple) {
        Multiple = multiple;
    }

    public String getFareType() {
        return FareType;
    }

    public void setFareType(String fareType) {
        FareType = fareType;
    }

    public String getVia() {
        return Via;
    }

    public void setVia(String via) {
        Via = via;
    }
}
