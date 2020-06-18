package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PNRDetails{

	@SerializedName("BusName")
	private String busName;

	@SerializedName("Origin")
	private String origin;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("DepartureTime")
	private String departureTime;

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

	public String getBusName(){
		return busName;
	}

	public String getOrigin(){
		return origin;
	}

	public String getDestination(){
		return destination;
	}

	public String getDepartureTime(){
		return departureTime;
	}

	public List<PaxListItem> getPaxList(){
		return paxList;
	}

	public String getTotalTickets(){
		return totalTickets;
	}

	public TransportDetails getTransportDetails(){
		return transportDetails;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public String getTotalAmount(){
		return totalAmount;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public String getTransportPNR(){
		return transportPNR;
	}
}