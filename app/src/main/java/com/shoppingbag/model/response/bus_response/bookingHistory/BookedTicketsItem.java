package com.shoppingbag.model.response.bus_response.bookingHistory;

import com.google.gson.annotations.SerializedName;

public class BookedTicketsItem{

	@SerializedName("Origin")
	private String origin;

	@SerializedName("TransportName")
	private String transportName;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("BookedDateTime")
	private String bookedDateTime;

	@SerializedName("TotalTickets")
	private String totalTickets;

	@SerializedName("TravelDate")
	private String travelDate;

	@SerializedName("Amount")
	private String amount;

	@SerializedName("PenaltyAmount")
	private String penaltyAmount;

	@SerializedName("TicketStatus")
	private String ticketStatus;

	@SerializedName("TransportId")
	private String transportId;

	@SerializedName("TransactionId")
	private String transactionId;

	public String getOrigin(){
		return origin;
	}

	public String getTransportName(){
		return transportName;
	}

	public String getDestination(){
		return destination;
	}

	public String getBookedDateTime(){
		return bookedDateTime;
	}

	public String getTotalTickets(){
		return totalTickets;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public String getAmount(){
		return amount;
	}

	public String getPenaltyAmount(){
		return penaltyAmount;
	}

	public String getTicketStatus(){
		return ticketStatus;
	}

	public String getTransportId(){
		return transportId;
	}

	public String getTransactionId(){
		return transactionId;
	}
}