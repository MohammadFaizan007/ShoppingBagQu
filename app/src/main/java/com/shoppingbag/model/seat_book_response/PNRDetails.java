package com.shoppingbag.model.seat_book_response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PNRDetails{

	@SerializedName("BusName")
	private String busName;

	@SerializedName("Origin")
	private String origin;

	@SerializedName("DepartureTime")
	private String departureTime;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("PaxList")
	private List<PaxListItem> paxList;

	@SerializedName("TotalTickets")
	private String totalTickets;

	@SerializedName("TransportDetails")
	private TransportDetails transportDetails;

	@SerializedName("TravelDate")
	private String travelDate;

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

	public void setDepartureTime(String departureTime){
		this.departureTime = departureTime;
	}

	public String getDepartureTime(){
		return departureTime;
	}

	public void setDestination(String destination){
		this.destination = destination;
	}

	public String getDestination(){
		return destination;
	}

	public void setPaxList(List<PaxListItem> paxList){
		this.paxList = paxList;
	}

	public List<PaxListItem> getPaxList(){
		return paxList;
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
}