package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

public class CancelTicketDetailsItem {

    @SerializedName("Origin")
    private String origin;

    @SerializedName("ClassCodeDesc")
    private String classCodeDesc;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("TicketNumber")
    private String ticketNumber;

    @SerializedName("SegmentId")
    private int segmentId;

    @SerializedName("DepartureDateTime")
    private String departureDateTime;

    @SerializedName("ArrivalDateTime")
    private String arrivalDateTime;

    @SerializedName("TotalTaxAmount")
    private int totalTaxAmount;

    @SerializedName("FlightNumber")
    private String flightNumber;

    @SerializedName("TicketStatus")
    private String ticketStatus;

    @SerializedName("BasicAmount")
    private int basicAmount;

    @SerializedName("GrossAmount")
    private int grossAmount;

    private boolean itemClick = false;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getClassCodeDesc() {
        return classCodeDesc;
    }

    public void setClassCodeDesc(String classCodeDesc) {
        this.classCodeDesc = classCodeDesc;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
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

    public int getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(int totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public int getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(int basicAmount) {
        this.basicAmount = basicAmount;
    }

    public int getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(int grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Override
    public String toString() {
        return
                "CancelTicketDetailsItem{" +
                        "origin = '" + origin + '\'' +
                        ",classCodeDesc = '" + classCodeDesc + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",ticketNumber = '" + ticketNumber + '\'' +
                        ",segmentId = '" + segmentId + '\'' +
                        ",departureDateTime = '" + departureDateTime + '\'' +
                        ",arrivalDateTime = '" + arrivalDateTime + '\'' +
                        ",totalTaxAmount = '" + totalTaxAmount + '\'' +
                        ",flightNumber = '" + flightNumber + '\'' +
                        ",ticketStatus = '" + ticketStatus + '\'' +
                        ",basicAmount = '" + basicAmount + '\'' +
                        ",grossAmount = '" + grossAmount + '\'' +
                        "}";
    }

    public boolean isItemClick() {
        return itemClick;
    }

    public void setItemClick(boolean itemClick) {
        this.itemClick = itemClick;
    }
}