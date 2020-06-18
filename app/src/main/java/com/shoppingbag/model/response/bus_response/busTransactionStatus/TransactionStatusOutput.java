package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

public class TransactionStatusOutput{

	@SerializedName("Remarks")
	private String remarks;

	@SerializedName("TransactionStatus")
	private String transactionStatus;

	@SerializedName("TicketDetails")
	private TicketDetails ticketDetails;

	public String getRemarks(){
		return remarks;
	}

	public String getTransactionStatus(){
		return transactionStatus;
	}

	public TicketDetails getTicketDetails(){
		return ticketDetails;
	}
}