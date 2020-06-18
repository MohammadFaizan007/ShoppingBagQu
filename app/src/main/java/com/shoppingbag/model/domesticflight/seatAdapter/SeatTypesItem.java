package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

public class SeatTypesItem {

    @SerializedName("SeatNos")
    private String seatNos;

    @SerializedName("SeatTypeName")
    private String seatTypeName;

    public String getSeatNos() {
        return seatNos;
    }

    public void setSeatNos(String seatNos) {
        this.seatNos = seatNos;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    @Override
    public String toString() {
        return
                "SeatTypesItem{" +
                        "seatNos = '" + seatNos + '\'' +
                        ",seatTypeName = '" + seatTypeName + '\'' +
                        "}";
    }
}