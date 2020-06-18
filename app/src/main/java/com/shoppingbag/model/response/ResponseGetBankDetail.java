package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseGetBankDetail{

	@SerializedName("response")
	private String response;

	@SerializedName("BankDetails")
	private BankDetails bankDetails;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setBankDetails(BankDetails bankDetails){
		this.bankDetails = bankDetails;
	}

	public BankDetails getBankDetails(){
		return bankDetails;
	}
}