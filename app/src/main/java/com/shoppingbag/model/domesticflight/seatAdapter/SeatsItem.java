package com.shoppingbag.model.domesticflight.seatAdapter;

import com.google.gson.annotations.SerializedName;

public class SeatsItem {

    @SerializedName("SeatNo")
    private String seatNo;

    @SerializedName("SeatCode")
    private String seatCode;

    @SerializedName("SeatAmount")
    private int seatAmount;

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getSeatAmount() {
        return seatAmount;
    }

    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;
    }

    @Override
    public String toString() {
        return
                "SeatsItem{" +
                        "seatNo = '" + seatNo + '\'' +
                        ",seatCode = '" + seatCode + '\'' +
                        ",seatAmount = '" + seatAmount + '\'' +
                        "}";
    }
}