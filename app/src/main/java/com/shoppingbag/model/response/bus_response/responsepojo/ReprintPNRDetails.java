package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReprintPNRDetails{

	@SerializedName("BusName")
	private String busName;

	@SerializedName("Origin")
	private String origin;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("DepartureTime")
	private String departureTime;

	@SerializedName("TotalTickets")
	private String totalTickets;

	@SerializedName("TransportDetails")
	private TransportDetails transportDetails;

	@SerializedName("TravelDate")
	private String travelDate;

	@SerializedName("ReprintPaxList")
	private List<ReprintPaxListItem> reprintPaxList;

	@SerializedName("TotalAmount")
	private String totalAmount;

	@SerializedName("TransactionId")
	private String transactionId;

	@SerializedName("TransportPNR")
	private String transportPNR;

	public void setBusName(String busName){
		this.busName = busName;
	}

	public String getBusName(){
		return busName;
	}

	public void setOrigin(String origin){
		this.origin = origin;
	}

	public String getOrigin(){
		return origin;
	}

	public void setDestination(String destination){
		this.destination = destination;
	}

	public String getDestination(){
		return destination;
	}

	public void setDepartureTime(String departureTime){
		this.departureTime = departureTime;
	}

	public String getDepartureTime(){
		return departureTime;
	}

	public void setTotalTickets(String totalTickets){
		this.totalTickets = totalTickets;
	}

	public String getTotalTickets(){
		return totalTickets;
	}

	public void setTransportDetails(TransportDetails transportDetails){
		this.transportDetails = transportDetails;
	}

	public TransportDetails getTransportDetails(){
		return transportDetails;
	}

	public void setTravelDate(String travelDate){
		this.travelDate = travelDate;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public void setReprintPaxList(List<ReprintPaxListItem> reprintPaxList){
		this.reprintPaxList = reprintPaxList;
	}

	public List<ReprintPaxListItem> getReprintPaxList(){
		return reprintPaxList;
	}

	public void setTotalAmount(String totalAmount){
		this.totalAmount = totalAmount;
	}

	public String getTotalAmount(){
		return totalAmount;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setTransportPNR(String transportPNR){
		this.transportPNR = transportPNR;
	}

	public String getTransportPNR(){
		return transportPNR;
	}

	@Override
 	public String toString(){
		return 
			"ReprintPNRDetails{" + 
			"busName = '" + busName + '\'' + 
			",origin = '" + origin + '\'' + 
			",destination = '" + destination + '\'' + 
			",departureTime = '" + departureTime + '\'' + 
			",totalTickets = '" + totalTickets + '\'' + 
			",transportDetails = '" + transportDetails + '\'' + 
			",travelDate = '" + travelDate + '\'' + 
			",reprintPaxList = '" + reprintPaxList + '\'' + 
			",totalAmount = '" + totalAmount + '\'' + 
			",transactionId = '" + transactionId + '\'' + 
			",transportPNR = '" + transportPNR + '\'' + 
			"}";
		}
}