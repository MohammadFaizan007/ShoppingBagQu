package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class CancellationPenaltyInput{

	@SerializedName("TransactionId")
	private String transactionId;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	@Override
 	public String toString(){
		return 
			"CancellationPenaltyInput{" + 
			"transactionId = '" + transactionId + '\'' + 
			"}";
		}
}