package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class PartialCancelTicketDetailsItem {

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("TicketNumber")
    private String ticketNumber;

    @SerializedName("SegmentId")
    private int segmentId;

    @SerializedName("FlightNumber")
    private String flightNumber;

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

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return
                "PartialCancelTicketDetailsItem{" +
                        "origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",ticketNumber = '" + ticketNumber + '\'' +
                        ",segmentId = '" + segmentId + '\'' +
                        ",flightNumber = '" + flightNumber + '\'' +
                        "}";
    }
}