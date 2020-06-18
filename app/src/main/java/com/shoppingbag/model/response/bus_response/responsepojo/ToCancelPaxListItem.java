package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class ToCancelPaxListItem{

	@SerializedName("PenatlyAmount")
	private String penatlyAmount;

	@SerializedName("TicketNumber")
	private String ticketNumber;

	@SerializedName("PassengerName")
	private String passengerName;

	@SerializedName("SeatNo")
	private String seatNo;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Age")
	private String age;

	@SerializedName("Fare")
	private String fare;

	public void setPenatlyAmount(String penatlyAmount){
		this.penatlyAmount = penatlyAmount;
	}

	public String getPenatlyAmount(){
		return penatlyAmount;
	}

	public void setTicketNumber(String ticketNumber){
		this.ticketNumber = ticketNumber;
	}

	public String getTicketNumber(){
		return ticketNumber;
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

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	public void setFare(String fare){
		this.fare = fare;
	}

	public String getFare(){
		return fare;
	}

	@Override
 	public String toString(){
		return 
			"ToCancelPaxListItem{" + 
			"penatlyAmount = '" + penatlyAmount + '\'' + 
			",ticketNumber = '" + ticketNumber + '\'' + 
			",passengerName = '" + passengerName + '\'' + 
			",seatNo = '" + seatNo + '\'' + 
			",gender = '" + gender + '\'' + 
			",age = '" + age + '\'' + 
			",fare = '" + fare + '\'' + 
			"}";
		}
}