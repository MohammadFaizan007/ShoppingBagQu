package com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm;

import com.google.gson.annotations.SerializedName;

public class BookingSegmentsItem {

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("SeatAmt")
    private String seatAmt;

    @SerializedName("PaxNumber ")
    private String paxNumber;

    @SerializedName("SeatNo")
    private String seatNo;

    @SerializedName("FlightNo")
    private String flightNo;

    @SerializedName("SeatCode")
    private String seatCode;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

    @SerializedName("Remark")
    private String remark;

    @SerializedName("FK_MemId")
    private String fK_MemId;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSeatAmt() {
        return seatAmt;
    }

    public void setSeatAmt(String seatAmt) {
        this.seatAmt = seatAmt;
    }

    public String getPaxNumber() {
        return paxNumber;
    }

    public void setPaxNumber(String paxNumber) {
        this.paxNumber = paxNumber;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getHermesPNR() {
        return hermesPNR;
    }

    public void setHermesPNR(String hermesPNR) {
        this.hermesPNR = hermesPNR;
    }

    public String getfK_MemId() {
        return fK_MemId;
    }

    public void setfK_MemId(String fK_MemId) {
        this.fK_MemId = fK_MemId;
    }

    @Override
    public String toString() {
        return
                "BookingSegmentsItem{" +
                        "airlineCode = '" + airlineCode + '\'' +
                        ",origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",seatAmt = '" + seatAmt + '\'' +
                        ",paxNumber  = '" + paxNumber + '\'' +
                        ",seatNo = '" + seatNo + '\'' +
                        ",flightNo = '" + flightNo + '\'' +
                        ",seatCode = '" + seatCode + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        ",FK_MemId = '" + fK_MemId + '\'' +
                        "}";
    }

    public String getAirlinePNR() {
        return airlinePNR;
    }

    public void setAirlinePNR(String airlinePNR) {
        this.airlinePNR = airlinePNR;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}