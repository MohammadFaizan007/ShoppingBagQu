package com.shoppingbag.model.response.bus_response.busTransactionStatus;

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

	public BoardingPoints getBoardingPoints(){
		return boardingPoints;
	}

	public String getTicketNumber(){
		return ticketNumber;
	}

	public String getPassengerName(){
		return passengerName;
	}

	public String getReportingTime(){
		return reportingTime;
	}

	public String getSeatType(){
		return seatType;
	}

	public String getServiceCharges(){
		return serviceCharges;
	}

	public String getSeatNo(){
		return seatNo;
	}

	public DroppingPoints getDroppingPoints(){
		return droppingPoints;
	}

	public String getGender(){
		return gender;
	}

	public String getAge(){
		return age;
	}

	public String getFare(){
		return fare;
	}
}