package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class BookingSegmentsItem {

    @SerializedName("SupplierId")
    private String supplierId;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("SeatPreferId")
    private String seatPreferId;

    @SerializedName("FlightId")
    private String flightId;

    @SerializedName("MealCode")
    private String mealCode;

    @SerializedName("FrequentFlyerId")
    private String frequentFlyerId;

    @SerializedName("FrequentFlyerNumber")
    private String frequentFlyerNumber;

    @SerializedName("SpecialServiceCode")
    private String specialServiceCode;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSeatPreferId() {
        return seatPreferId;
    }

    public void setSeatPreferId(String seatPreferId) {
        this.seatPreferId = seatPreferId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getMealCode() {
        return mealCode;
    }

    public void setMealCode(String mealCode) {
        this.mealCode = mealCode;
    }

    public String getFrequentFlyerId() {
        return frequentFlyerId;
    }

    public void setFrequentFlyerId(String frequentFlyerId) {
        this.frequentFlyerId = frequentFlyerId;
    }

    public String getFrequentFlyerNumber() {
        return frequentFlyerNumber;
    }

    public void setFrequentFlyerNumber(String frequentFlyerNumber) {
        this.frequentFlyerNumber = frequentFlyerNumber;
    }

    public String getSpecialServiceCode() {
        return specialServiceCode;
    }

    public void setSpecialServiceCode(String specialServiceCode) {
        this.specialServiceCode = specialServiceCode;
    }

    @Override
    public String toString() {
        return
                "BookingSegmentsItem{" +
                        "supplierId = '" + supplierId + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",seatPreferId = '" + seatPreferId + '\'' +
                        ",flightId = '" + flightId + '\'' +
                        ",mealCode = '" + mealCode + '\'' +
                        ",frequentFlyerId = '" + frequentFlyerId + '\'' +
                        ",frequentFlyerNumber = '" + frequentFlyerNumber + '\'' +
                        ",specialServiceCode = '" + specialServiceCode + '\'' +
                        "}";
    }
}