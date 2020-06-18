package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

public class BookedPNRsItem {

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("TotalSegments")
    private int totalSegments;

    @SerializedName("BookedDateTime")
    private String bookedDateTime;

    @SerializedName("PNRStatus")
    private String pNRStatus;

    @SerializedName("BookedTerminal")
    private String bookedTerminal;

    @SerializedName("AirlineName")
    private String airlineName;

    @SerializedName("BookingType")
    private String bookingType;

    @SerializedName("GrossAmount")
    private int grossAmount;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

    @SerializedName("HermesPNR")
    private String hermesPNR;

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

    public int getTotalSegments() {
        return totalSegments;
    }

    public void setTotalSegments(int totalSegments) {
        this.totalSegments = totalSegments;
    }

    public String getBookedDateTime() {
        return bookedDateTime;
    }

    public void setBookedDateTime(String bookedDateTime) {
        this.bookedDateTime = bookedDateTime;
    }

    public String getPNRStatus() {
        return pNRStatus;
    }

    public void setPNRStatus(String pNRStatus) {
        this.pNRStatus = pNRStatus;
    }

    public String getBookedTerminal() {
        return bookedTerminal;
    }

    public void setBookedTerminal(String bookedTerminal) {
        this.bookedTerminal = bookedTerminal;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public int getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(int grossAmount) {
        this.grossAmount = grossAmount;
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
                "BookedPNRsItem{" +
                        "origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",totalSegments = '" + totalSegments + '\'' +
                        ",bookedDateTime = '" + bookedDateTime + '\'' +
                        ",pNRStatus = '" + pNRStatus + '\'' +
                        ",bookedTerminal = '" + bookedTerminal + '\'' +
                        ",airlineName = '" + airlineName + '\'' +
                        ",bookingType = '" + bookingType + '\'' +
                        ",grossAmount = '" + grossAmount + '\'' +
                        ",airlinePNR = '" + airlinePNR + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        "}";
    }
}