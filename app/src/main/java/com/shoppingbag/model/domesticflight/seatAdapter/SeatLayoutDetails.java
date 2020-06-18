package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatLayoutDetails {

    @SerializedName("BookedSeats")
    private String bookedSeats;

    @SerializedName("SeatTypes")
    private List<SeatTypesItem> seatTypes;

    @SerializedName("SeatLayout")
    private SeatLayout seatLayout;

    @SerializedName("BlockedSeats")
    private String blockedSeats;

    @SerializedName("AvailableSeats")
    private String availableSeats;

    public String getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(String bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public List<SeatTypesItem> getSeatTypes() {
        return seatTypes;
    }

    public void setSeatTypes(List<SeatTypesItem> seatTypes) {
        this.seatTypes = seatTypes;
    }

    public SeatLayout getSeatLayout() {
        return seatLayout;
    }

    public void setSeatLayout(SeatLayout seatLayout) {
        this.seatLayout = seatLayout;
    }

    public String getBlockedSeats() {
        return blockedSeats;
    }

    public void setBlockedSeats(String blockedSeats) {
        this.blockedSeats = blockedSeats;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return
                "SeatLayoutDetails{" +
                        "bookedSeats = '" + bookedSeats + '\'' +
                        ",seatTypes = '" + seatTypes + '\'' +
                        ",seatLayout = '" + seatLayout + '\'' +
                        ",blockedSeats = '" + blockedSeats + '\'' +
                        ",availableSeats = '" + availableSeats + '\'' +
                        "}";
    }
}