package com.shoppingbag.model.seat_book_response;


import com.google.gson.annotations.SerializedName;

public class PaxListItem{

	@SerializedName("BoardingPoints")
	private BoardingPoints boardingPoints;

	@SerializedName("TicketNumber")
	private String ticketNumber;

	@SerializedName("PassengerName")
	private String passengerName;

	@SerializedName("ReportingTime")
	private String reportingTime;

	@SerializedName("SeatType")
	private String seatType;

	@SerializedName("ServiceCharges")
	private String serviceCharges;

	@SerializedName("SeatNo")
	private String seatNo;

	@SerializedName("DroppingPoints")
	private DroppingPoints droppingPoints;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Age")
	private String age;

	@SerializedName("Fare")
	private String fare;

	public void setBoardingPoints(BoardingPoints boardingPoints){
		this.boardingPoints = boardingPoints;
	}

	public BoardingPoints getBoardingPoints(){
		return boardingPoints;
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

	public void setReportingTime(String reportingTime){
		this.reportingTime = reportingTime;
	}

	public String getReportingTime(){
		return reportingTime;
	}

	public void setSeatType(String seatType){
		this.seatType = seatType;
	}

	public String getSeatType(){
		return seatType;
	}

	public void setServiceCharges(String serviceCharges){
		this.serviceCharges = serviceCharges;
	}

	public String getServiceCharges(){
		return serviceCharges;
	}

	public void setSeatNo(String seatNo){
		this.seatNo = seatNo;
	}

	public String getSeatNo(){
		return seatNo;
	}

	public void setDroppingPoints(DroppingPoints droppingPoints){
		this.droppingPoints = droppingPoints;
	}

	public DroppingPoints getDroppingPoints(){
		return droppingPoints;
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
}