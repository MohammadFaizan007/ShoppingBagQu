package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PassengerDetailsItem {

    @SerializedName("BookingSegments")
    private List<BookingSegmentsItem> bookingSegments;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LCCBaggageRequest")
    private String lCCBaggageRequest="";

    @SerializedName("Title")
    private String title;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("LCCMealsRequest")
    private String lCCMealsRequest="";

    @SerializedName("PassengerType")
    private String passengerType;

    @SerializedName("OtherSSRRequest ")
    private String otherSSRRequest="";

    @SerializedName("IdentityProofId")
    private String identityProofId;

    @SerializedName("SeatRequest")
    private String seatRequest="";

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("DateofBirth")
    private String dateofBirth;

    @SerializedName("IdentityProofNumber")
    private String identityProofNumber;

    @SerializedName("Age")
    private int age;

    public List<BookingSegmentsItem> getBookingSegments() {
        return bookingSegments;
    }

    public void setBookingSegments(List<BookingSegmentsItem> bookingSegments) {
        this.bookingSegments = bookingSegments;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLCCBaggageRequest() {
        return lCCBaggageRequest;
    }

    public void setLCCBaggageRequest(String lCCBaggageRequest) {
        this.lCCBaggageRequest = lCCBaggageRequest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLCCMealsRequest() {
        return lCCMealsRequest;
    }

    public void setLCCMealsRequest(String lCCMealsRequest) {
        this.lCCMealsRequest = lCCMealsRequest;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getOtherSSRRequest() {
        return otherSSRRequest;
    }

    public void setOtherSSRRequest(String otherSSRRequest) {
        this.otherSSRRequest = otherSSRRequest;
    }

    public String getIdentityProofId() {
        return identityProofId;
    }

    public void setIdentityProofId(String identityProofId) {
        this.identityProofId = identityProofId;
    }

    public String getSeatRequest() {
        return seatRequest;
    }

    public void setSeatRequest(String seatRequest) {
        this.seatRequest = seatRequest;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getIdentityProofNumber() {
        return identityProofNumber;
    }

    public void setIdentityProofNumber(String identityProofNumber) {
        this.identityProofNumber = identityProofNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return
                "PassengerDetailsItem{" +
                        "bookingSegments = '" + bookingSegments + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",lCCBaggageRequest = '" + lCCBaggageRequest + '\'' +
                        ",title = '" + title + '\'' +
                        ",gender = '" + gender + '\'' +
                        ",lCCMealsRequest = '" + lCCMealsRequest + '\'' +
                        ",passengerType = '" + passengerType + '\'' +
                        ",otherSSRRequest  = '" + otherSSRRequest + '\'' +
                        ",identityProofId = '" + identityProofId + '\'' +
                        ",seatRequest = '" + seatRequest + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",dateofBirth = '" + dateofBirth + '\'' +
                        ",identityProofNumber = '" + identityProofNumber + '\'' +
                        ",age = '" + age + '\'' +
                        "}";
    }
}