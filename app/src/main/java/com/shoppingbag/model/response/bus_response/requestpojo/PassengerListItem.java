package com.shoppingbag.model.response.bus_response.requestpojo;

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
	private double fare;

	public void setSeatTypeId(int seatTypeId){
		this.seatTypeId = seatTypeId;
	}

	public int getSeatTypeId(){
		return seatTypeId;
	}

	public void setBoardingId(String boardingId){
		this.boardingId = boardingId;
	}

	public String getBoardingId(){
		return boardingId;
	}

	public void setPassengerName(String passengerName){
		this.passengerName = passengerName;
	}

	public String getPassengerName(){
		return passengerName;
	}

	public void setSeatNo(String seatNo){
		this.seatNo = seatNo;
	}

	public String getSeatNo(){
		return seatNo;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setDroppingId(String droppingId){
		this.droppingId = droppingId;
	}

	public String getDroppingId(){
		return droppingId;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	public void setFare(double fare){
		this.fare = fare;
	}

	public double getFare(){
		return fare;
	}

	@Override
	public String toString(){
		return
				"PassengerListItem{" +
						"seatTypeId = '" + seatTypeId + '\'' +
						",boardingId = '" + boardingId + '\'' +
						",passengerName = '" + passengerName + '\'' +
						",seatNo = '" + seatNo + '\'' +
						",gender = '" + gender + '\'' +
						",droppingId = '" + droppingId + '\'' +
						",age = '" + age + '\'' +
						",fare = '" + fare + '\'' +
						"}";
	}
}