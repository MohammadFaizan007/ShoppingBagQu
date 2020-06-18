package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

public class ResponseBusBookingTransation{

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("TransactionStatusOutput")
	private TransactionStatusOutput transactionStatusOutput;

	public String getResponseStatus(){
		return responseStatus;
	}

	public TransactionStatusOutput getTransactionStatusOutput(){
		return transactionStatusOutput;
	}
}