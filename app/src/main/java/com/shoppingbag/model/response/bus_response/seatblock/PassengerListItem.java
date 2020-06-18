package com.shoppingbag.model.response.bus_response.seatblock;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PassengerListItem implements Serializable {

    @SerializedName("SeatTypeId")
    private int seatTypeId;

    @SerializedName("BoardingId")
    private String boardingId;

    @SerializedName("PassengerName")
    private String passengerName;

    @SerializedName("SeatNo")
    private String seatNo;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("DroppingId")
    private String droppingId;

    @SerializedName("Age")
    private int age;

    @SerializedName("Fare")
    private int fare;

    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

    public int getSeatTypeId() {
        return seatTypeId;
    }

    public void setBoardingId(String boardingId) {
        this.boardingId = boardingId;
    }

    public String getBoardingId() {
        return boardingId;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setDroppingId(String droppingId) {
        this.droppingId = droppingId;
    }

    public String getDroppingId() {
        return droppingId;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getFare() {
        return fare;
    }
}