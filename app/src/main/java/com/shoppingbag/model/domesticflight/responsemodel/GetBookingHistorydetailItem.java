package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class GetBookingHistorydetailItem {
    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("PassengerId")
    private String passengerId;

    @SerializedName("TerminalName")
    private String terminalName;

    @SerializedName("SeatAmt")
    private String seatAmt;

    @SerializedName("TotalAMount")
    private String totalAMount;

    @SerializedName("AirlineName")
    private String airlineName;

    @SerializedName("DepartingDate")
    private String departingDate;

    @SerializedName("SeatCode")
    private String seatCode;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("historydetails")
    private Object historydetails;

    @SerializedName("BoookingDate")
    private String boookingDate;

    @SerializedName("Pk_FlightResponseId")
    private String pkFlightResponseId;

    @SerializedName("Remarks")
    private String remarks;

    @SerializedName("BookSeatButton")
    private Object bookSeatButton;

    @SerializedName("SeatNo")
    private String seatNo;

    @SerializedName("FromDate")
    private Object fromDate;

    @SerializedName("ToDate")
    private Object toDate;

    @SerializedName("ButtonDisplay")
    private Object buttonDisplay;

    @SerializedName("BookingType")
    private String bookingType;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

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

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getSeatAmt() {
        return seatAmt;
    }

    public void setSeatAmt(String seatAmt) {
        this.seatAmt = seatAmt;
    }

    public String getTotalAMount() {
        return totalAMount;
    }

    public void setTotalAMount(String totalAMount) {
        this.totalAMount = totalAMount;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getDepartingDate() {
        return departingDate;
    }

    public void setDepartingDate(String departingDate) {
        this.departingDate = departingDate;
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

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Object getHistorydetails() {
        return historydetails;
    }

    public void setHistorydetails(Object historydetails) {
        this.historydetails = historydetails;
    }

    public String getBoookingDate() {
        return boookingDate;
    }

    public void setBoookingDate(String boookingDate) {
        this.boookingDate = boookingDate;
    }

    public String getPkFlightResponseId() {
        return pkFlightResponseId;
    }

    public void setPkFlightResponseId(String pkFlightResponseId) {
        this.pkFlightResponseId = pkFlightResponseId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Object getBookSeatButton() {
        return bookSeatButton;
    }

    public void setBookSeatButton(Object bookSeatButton) {
        this.bookSeatButton = bookSeatButton;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public Object getButtonDisplay() {
        return buttonDisplay;
    }

    public void setButtonDisplay(Object buttonDisplay) {
        this.buttonDisplay = buttonDisplay;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getAirlinePNR() {
        return airlinePNR;
    }

    public void setAirlinePNR(String airlinePNR) {
        this.airlinePNR = airlinePNR;
    }

    @Override
    public String toString() {
        return
                "GetBookingHistorydetailItem{" +
                        "origin = '" + origin + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",passengerId = '" + passengerId + '\'' +
                        ",terminalName = '" + terminalName + '\'' +
                        ",seatAmt = '" + seatAmt + '\'' +
                        ",totalAMount = '" + totalAMount + '\'' +
                        ",airlineName = '" + airlineName + '\'' +
                        ",departingDate = '" + departingDate + '\'' +
                        ",seatCode = '" + seatCode + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        ",airlineCode = '" + airlineCode + '\'' +
                        ",historydetails = '" + historydetails + '\'' +
                        ",boookingDate = '" + boookingDate + '\'' +
                        ",pk_FlightResponseId = '" + pkFlightResponseId + '\'' +
                        ",remarks = '" + remarks + '\'' +
                        ",bookSeatButton = '" + bookSeatButton + '\'' +
                        ",seatNo = '" + seatNo + '\'' +
                        ",fromDate = '" + fromDate + '\'' +
                        ",toDate = '" + toDate + '\'' +
                        ",buttonDisplay = '" + buttonDisplay + '\'' +
                        ",bookingType = '" + bookingType + '\'' +
                        ",airlinePNR = '" + airlinePNR + '\'' +
                        "}";
    }
}