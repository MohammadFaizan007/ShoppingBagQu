package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeatLayoutDetails implements Serializable {

    @SerializedName("BookedSeats")
    private List<BookedSeatsItem> bookedSeats;

    @SerializedName("SeatLayout")
    private SeatLayout seatLayout;

    @SerializedName("LayoutFlag")
    private String layoutFlag;

    @SerializedName("AvailableSeats")
    private List<AvailableSeatsItem> availableSeats;

    public void setBookedSeats(List<BookedSeatsItem> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public List<BookedSeatsItem> getBookedSeats() {
        return bookedSeats;
    }

    public void setSeatLayout(SeatLayout seatLayout) {
        this.seatLayout = seatLayout;
    }

    public SeatLayout getSeatLayout() {
        return seatLayout;
    }

    public void setLayoutFlag(String layoutFlag) {
        this.layoutFlag = layoutFlag;
    }

    public String getLayoutFlag() {
        return layoutFlag;
    }

    public void setAvailableSeats(List<AvailableSeatsItem> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<AvailableSeatsItem> getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public String toString() {
        return
                "SeatLayoutDetails{" +
                        "bookedSeats = '" + bookedSeats + '\'' +
                        ",seatLayout = '" + seatLayout + '\'' +
                        ",layoutFlag = '" + layoutFlag + '\'' +
                        ",availableSeats = '" + availableSeats + '\'' +
                        "}";
    }
}