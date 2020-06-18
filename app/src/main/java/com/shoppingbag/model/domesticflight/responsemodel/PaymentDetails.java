package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class PaymentDetails{

	@SerializedName("CurrencyCode")
	private String currencyCode;

	@SerializedName("Amount")
	private String amount;

	@SerializedName("AirlinePaymentDetails")
	private AirlinePaymentDetails airlinePaymentDetails;

	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode(){
		return currencyCode;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setAirlinePaymentDetails(AirlinePaymentDetails airlinePaymentDetails){
		this.airlinePaymentDetails = airlinePaymentDetails;
	}

	public AirlinePaymentDetails getAirlinePaymentDetails(){
		return airlinePaymentDetails;
	}
}