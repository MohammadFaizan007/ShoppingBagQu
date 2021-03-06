package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingDetails{

	@SerializedName("TotalTickets")
	private int totalTickets;

	@SerializedName("TravelDate")
	private String travelDate;

	@SerializedName("PassengerList")
	private List<PassengerListItem> passengerList;

	@SerializedName("TotalAmount")
	private double totalAmount;

	@SerializedName("ScheduleId")
	private String scheduleId;

	@SerializedName("StationId")
	private String stationId;

	@SerializedName("TransportId")
	private int transportId;

	public void setTotalTickets(int totalTickets){
		this.totalTickets = totalTickets;
	}

	public int getTotalTickets(){
		return totalTickets;
	}

	public void setTravelDate(String travelDate){
		this.travelDate = travelDate;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public void setPassengerList(List<PassengerListItem> passengerList){
		this.passengerList = passengerList;
	}

	public List<PassengerListItem> getPassengerList(){
		return passengerList;
	}

	public void setTotalAmount(double totalAmount){
		this.totalAmount = totalAmount;
	}

	public double getTotalAmount(){
		return totalAmount;
	}

	public void setScheduleId(String scheduleId){
		this.scheduleId = scheduleId;
	}

	public String getScheduleId(){
		return scheduleId;
	}

	public void setStationId(String stationId){
		this.stationId = stationId;
	}

	public String getStationId(){
		return stationId;
	}

	public void setTransportId(int transportId){
		this.transportId = transportId;
	}

	public int getTransportId(){
		return transportId;
	}

	@Override
 	public String toString(){
		return 
			"BookingDetails{" + 
			"totalTickets = '" + totalTickets + '\'' + 
			",travelDate = '" + travelDate + '\'' + 
			",passengerList = '" + passengerList + '\'' + 
			",totalAmount = '" + totalAmount + '\'' + 
			",scheduleId = '" + scheduleId + '\'' + 
			",stationId = '" + stationId + '\'' + 
			",transportId = '" + transportId + '\'' + 
			"}";
		}
}