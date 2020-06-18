package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AvailSegmentsItem implements Serializable {

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Currency_Conversion_Rate")
    private String currencyConversionRate;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("OriginAirportTerminal")
    private String originAirportTerminal;

    @SerializedName("AvailPaxFareDetails")
    private List<AvailPaxFareDetailsItem> availPaxFareDetails;

    @SerializedName("Duration")
    private String duration;

    @SerializedName("Via")
    private String via;

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("CurrencyCode")
    private String currencyCode;

    @SerializedName("AirCraftType")
    private String airCraftType;

    @SerializedName("SupplierId")
    private String supplierId;

    @SerializedName("DestinationAirportTerminal")
    private String destinationAirportTerminal;

    @SerializedName("DepartureDateTime")
    private String departureDateTime;

    @SerializedName("ArrivalDateTime")
    private String arrivalDateTime;

    @SerializedName("FlightId")
    private String flightId;

    @SerializedName("FlightNumber")
    private String flightNumber;

    @SerializedName("NumberofStops")
    private int numberofStops;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCurrencyConversionRate() {
        return currencyConversionRate;
    }

    public void setCurrencyConversionRate(String currencyConversionRate) {
        this.currencyConversionRate = currencyConversionRate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOriginAirportTerminal() {
        return originAirportTerminal;
    }

    public void setOriginAirportTerminal(String originAirportTerminal) {
        this.originAirportTerminal = originAirportTerminal;
    }

    public List<AvailPaxFareDetailsItem> getAvailPaxFareDetails() {
        return availPaxFareDetails;
    }

    public void setAvailPaxFareDetails(List<AvailPaxFareDetailsItem> availPaxFareDetails) {
        this.availPaxFareDetails = availPaxFareDetails;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAirCraftType() {
        return airCraftType;
    }

    public void setAirCraftType(String airCraftType) {
        this.airCraftType = airCraftType;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDestinationAirportTerminal() {
        return destinationAirportTerminal;
    }

    public void setDestinationAirportTerminal(String destinationAirportTerminal) {
        this.destinationAirportTerminal = destinationAirportTerminal;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getNumberofStops() {
        return numberofStops;
    }

    public void setNumberofStops(int numberofStops) {
        this.numberofStops = numberofStops;
    }

    @Override
    public String toString() {
        return
                "AvailSegmentsItem{" +
                        "origin = '" + origin + '\'' +
                        ",currency_Conversion_Rate = '" + currencyConversionRate + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",originAirportTerminal = '" + originAirportTerminal + '\'' +
                        ",availPaxFareDetails = '" + availPaxFareDetails + '\'' +
                        ",duration = '" + duration + '\'' +
                        ",via = '" + via + '\'' +
                        ",airlineCode = '" + airlineCode + '\'' +
                        ",currencyCode = '" + currencyCode + '\'' +
                        ",airCraftType = '" + airCraftType + '\'' +
                        ",supplierId = '" + supplierId + '\'' +
                        ",destinationAirportTerminal = '" + destinationAirportTerminal + '\'' +
                        ",departureDateTime = '" + departureDateTime + '\'' +
                        ",arrivalDateTime = '" + arrivalDateTime + '\'' +
                        ",flightId = '" + flightId + '\'' +
                        ",flightNumber = '" + flightNumber + '\'' +
                        ",numberofStops = '" + numberofStops + '\'' +
                        "}";
    }
}