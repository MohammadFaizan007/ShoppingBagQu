package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class CancellationInput{

	@SerializedName("PenaltyAmount")
	private String penaltyAmount;

	@SerializedName("TotalTicketsToCancel")
	private String totalTicketsToCancel;

	@SerializedName("TransactionId")
	private String transactionId;

	@SerializedName("TicketNumbers")
	private String ticketNumbers;

	@SerializedName("TransportId")
	private String transportId;

	public void setPenaltyAmount(String penaltyAmount){
		this.penaltyAmount = penaltyAmount;
	}

	public String getPenaltyAmount(){
		return penaltyAmount;
	}

	public void setTotalTicketsToCancel(String totalTicketsToCancel){
		this.totalTicketsToCancel = totalTicketsToCancel;
	}

	public String getTotalTicketsToCancel(){
		return totalTicketsToCancel;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setTicketNumbers(String ticketNumbers){
		this.ticketNumbers = ticketNumbers;
	}

	public String getTicketNumbers(){
		return ticketNumbers;
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
			"CancellationInput{" + 
			"penaltyAmount = '" + penaltyAmount + '\'' + 
			",totalTicketsToCancel = '" + totalTicketsToCancel + '\'' + 
			",transactionId = '" + transactionId + '\'' + 
			",ticketNumbers = '" + ticketNumbers + '\'' + 
			",transportId = '" + transportId + '\'' + 
			"}";
		}
}