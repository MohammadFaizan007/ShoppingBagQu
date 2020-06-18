package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class CancellationPolicyInput{

	@SerializedName("TravelDate")
	private String travelDate;

	@SerializedName("ScheduleId")
	private String scheduleId;

	@SerializedName("TripId")
	private String tripId;

	@SerializedName("TransportId")
	private String transportId;

	public void setTravelDate(String travelDate){
		this.travelDate = travelDate;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public void setScheduleId(String scheduleId){
		this.scheduleId = scheduleId;
	}

	public String getScheduleId(){
		return scheduleId;
	}

	public void setTripId(String tripId){
		this.tripId = tripId;
	}

	public String getTripId(){
		return tripId;
	}

	public void setTransportId(String transportId){
		this.transportId = transportId;
	}

	public String getTransportId(){
		return transportId;
	}

	@Override
 	public String toString(){
		return 
			"CancellationPolicyInput{" + 
			"travelDate = '" + travelDate + '\'' + 
			",scheduleId = '" + scheduleId + '\'' + 
			",tripId = '" + tripId + '\'' + 
			",transportId = '" + transportId + '\'' + 
			"}";
		}
}