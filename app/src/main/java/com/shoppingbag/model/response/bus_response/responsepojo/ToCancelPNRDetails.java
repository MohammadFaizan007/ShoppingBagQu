package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToCancelPNRDetails{

	@SerializedName("Origin")
	private String origin;

	@SerializedName("TransportName")
	private String transportName;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("BookedDate")
	private String bookedDate;

	@SerializedName("ToCancelPaxList")
	private List<ToCancelPaxListItem> toCancelPaxList;

	@SerializedName("PNRStatus")
	private String pNRStatus;

	@SerializedName("TravelDate")
	private String travelDate;

	@SerializedName("DepatureTime")
	private String depatureTime;

	public void setOrigin(String origin){
		this.origin = origin;
	}

	public String getOrigin(){
		return origin;
	}

	public void setTransportName(String transportName){
		this.transportName = transportName;
	}

	public String getTransportName(){
		return transportName;
	}

	public void setDestination(String destination){
		this.destination = destination;
	}

	public String getDestination(){
		return destination;
	}

	public void setBookedDate(String bookedDate){
		this.bookedDate = bookedDate;
	}

	public String getBookedDate(){
		return bookedDate;
	}

	public void setToCancelPaxList(List<ToCancelPaxListItem> toCancelPaxList){
		this.toCancelPaxList = toCancelPaxList;
	}

	public List<ToCancelPaxListItem> getToCancelPaxList(){
		return toCancelPaxList;
	}

	public void setPNRStatus(String pNRStatus){
		this.pNRStatus = pNRStatus;
	}

	public String getPNRStatus(){
		return pNRStatus;
	}

	public void setTravelDate(String travelDate){
		this.travelDate = travelDate;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public void setDepatureTime(String depatureTime){
		this.depatureTime = depatureTime;
	}

	public String getDepatureTime(){
		return depatureTime;
	}

	@Override
 	public String toString(){
		return 
			"ToCancelPNRDetails{" + 
			"origin = '" + origin + '\'' + 
			",transportName = '" + transportName + '\'' + 
			",destination = '" + destination + '\'' + 
			",bookedDate = '" + bookedDate + '\'' + 
			",toCancelPaxList = '" + toCancelPaxList + '\'' + 
			",pNRStatus = '" + pNRStatus + '\'' + 
			",travelDate = '" + travelDate + '\'' + 
			",depatureTime = '" + depatureTime + '\'' + 
			"}";
		}
}