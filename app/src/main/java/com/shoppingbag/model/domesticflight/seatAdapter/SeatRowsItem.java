package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatRowsItem {

    @SerializedName("Seats")
    private List<SeatsItem> seats;

    public List<SeatsItem> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatsItem> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return
                "SeatRowsItem{" +
                        "seats = '" + seats + '\'' +
                        "}";
    }
}