package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AvailabilityInput implements Serializable {

    @SerializedName("JourneyDetails")
    private List<JourneyDetailsItem> journeyDetails;

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("Optional1")
    private String optional1;

    @SerializedName("Optional3")
    private String optional3;

    @SerializedName("Optional2")
    private String optional2;

    @SerializedName("InfantCount")
    private int infantCount;

    @SerializedName("AdultCount")
    private int adultCount;

    @SerializedName("ChildCount")
    private int childCount;

    @SerializedName("ResidentofIndia")
    private int residentofIndia;

    @SerializedName("ClassType")
    private String classType;

    @SerializedName("BookingType")
    private String bookingType;

    public List<JourneyDetailsItem> getJourneyDetails() {
        return journeyDetails;
    }

    public void setJourneyDetails(List<JourneyDetailsItem> journeyDetails) {
        this.journeyDetails = journeyDetails;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getOptional1() {
        return optional1;
    }

    public void setOptional1(String optional1) {
        this.optional1 = optional1;
    }

    public String getOptional3() {
        return optional3;
    }

    public void setOptional3(String optional3) {
        this.optional3 = optional3;
    }

    public String getOptional2() {
        return optional2;
    }

    public void setOptional2(String optional2) {
        this.optional2 = optional2;
    }

    public int getInfantCount() {
        return infantCount;
    }

    public void setInfantCount(int infantCount) {
        this.infantCount = infantCount;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getResidentofIndia() {
        return residentofIndia;
    }

    public void setResidentofIndia(int residentofIndia) {
        this.residentofIndia = residentofIndia;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    @Override
    public String toString() {
        return
                "AvailabilityInput{" +
                        "journeyDetails = '" + journeyDetails + '\'' +
                        ",airlineCode = '" + airlineCode + '\'' +
                        ",optional1 = '" + optional1 + '\'' +
                        ",optional3 = '" + optional3 + '\'' +
                        ",optional2 = '" + optional2 + '\'' +
                        ",infantCount = '" + infantCount + '\'' +
                        ",adultCount = '" + adultCount + '\'' +
                        ",childCount = '" + childCount + '\'' +
                        ",residentofIndia = '" + residentofIndia + '\'' +
                        ",classType = '" + classType + '\'' +
                        ",bookingType = '" + bookingType + '\'' +
                        "}";
    }
}