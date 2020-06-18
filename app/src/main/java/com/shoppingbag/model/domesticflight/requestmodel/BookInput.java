package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookInput {

    @SerializedName("FSCMealsRequest")
    private FSCMealsRequest fSCMealsRequest;

    @SerializedName("TotalAmount")
    private int totalAmount;

    @SerializedName("NotifyBySMS")
    private int notifyBySMS;

    @SerializedName("SpecialServiceRequest")
    private SpecialServiceRequest specialServiceRequest;

    @SerializedName("CustomerDetails")
    private CustomerDetails customerDetails;

    @SerializedName("NotifyByMail")
    private int notifyByMail;

    @SerializedName("SpecialRemarks")
    private String specialRemarks;

    @SerializedName("FlightBookingDetails")
    private List<FlightBookingDetailsItem> flightBookingDetails;

    @SerializedName("InfantCount")
    private int infantCount;

    @SerializedName("AdultCount")
    private int adultCount;

    @SerializedName("ChildCount")
    private int childCount;

    @SerializedName("BookingType")
    private String bookingType;

    @SerializedName("FrequentFlyerRequest")
    private FrequentFlyerRequest frequentFlyerRequest;

    public FSCMealsRequest getFSCMealsRequest() {
        return fSCMealsRequest;
    }

    public void setFSCMealsRequest(FSCMealsRequest fSCMealsRequest) {
        this.fSCMealsRequest = fSCMealsRequest;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNotifyBySMS() {
        return notifyBySMS;
    }

    public void setNotifyBySMS(int notifyBySMS) {
        this.notifyBySMS = notifyBySMS;
    }

    public SpecialServiceRequest getSpecialServiceRequest() {
        return specialServiceRequest;
    }

    public void setSpecialServiceRequest(SpecialServiceRequest specialServiceRequest) {
        this.specialServiceRequest = specialServiceRequest;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public int getNotifyByMail() {
        return notifyByMail;
    }

    public void setNotifyByMail(int notifyByMail) {
        this.notifyByMail = notifyByMail;
    }

    public String getSpecialRemarks() {
        return specialRemarks;
    }

    public void setSpecialRemarks(String specialRemarks) {
        this.specialRemarks = specialRemarks;
    }

    public List<FlightBookingDetailsItem> getFlightBookingDetails() {
        return flightBookingDetails;
    }

    public void setFlightBookingDetails(List<FlightBookingDetailsItem> flightBookingDetails) {
        this.flightBookingDetails = flightBookingDetails;
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

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public FrequentFlyerRequest getFrequentFlyerRequest() {
        return frequentFlyerRequest;
    }

    public void setFrequentFlyerRequest(FrequentFlyerRequest frequentFlyerRequest) {
        this.frequentFlyerRequest = frequentFlyerRequest;
    }

    @Override
    public String toString() {
        return
                "BookInput{" +
                        "fSCMealsRequest = '" + fSCMealsRequest + '\'' +
                        ",totalAmount = '" + totalAmount + '\'' +
                        ",notifyBySMS = '" + notifyBySMS + '\'' +
                        ",specialServiceRequest = '" + specialServiceRequest + '\'' +
                        ",customerDetails = '" + customerDetails + '\'' +
                        ",notifyByMail = '" + notifyByMail + '\'' +
                        ",specialRemarks = '" + specialRemarks + '\'' +
                        ",flightBookingDetails = '" + flightBookingDetails + '\'' +
                        ",infantCount = '" + infantCount + '\'' +
                        ",adultCount = '" + adultCount + '\'' +
                        ",childCount = '" + childCount + '\'' +
                        ",bookingType = '" + bookingType + '\'' +
                        ",frequentFlyerRequest = '" + frequentFlyerRequest + '\'' +
                        "}";
    }
}