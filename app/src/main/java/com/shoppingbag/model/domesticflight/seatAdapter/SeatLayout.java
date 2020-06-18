package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatLayout {

    @SerializedName("SeatRows")
    private List<SeatRowsItem> seatRows;

    public List<SeatRowsItem> getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(List<SeatRowsItem> seatRows) {
        this.seatRows = seatRows;
    }

    @Override
    public String toString() {
        return
                "SeatLayout{" +
                        "seatRows = '" + seatRows + '\'' +
                        "}";
    }
}