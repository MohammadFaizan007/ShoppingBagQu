package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class AirlinePaymentDetails{

	@SerializedName("CreditCardDetails")
	private CreditCardDetails creditCardDetails;

	@SerializedName("TicketingPCC")
	private String ticketingPCC;

	@SerializedName("PaymentType")
	private String paymentType;

	@SerializedName("VendorCode")
	private String vendorCode;

	public void setCreditCardDetails(CreditCardDetails creditCardDetails){
		this.creditCardDetails = creditCardDetails;
	}

	public CreditCardDetails getCreditCardDetails(){
		return creditCardDetails;
	}

	public void setTicketingPCC(String ticketingPCC){
		this.ticketingPCC = ticketingPCC;
	}

	public String getTicketingPCC(){
		return ticketingPCC;
	}

	public void setPaymentType(String paymentType){
		this.paymentType = paymentType;
	}

	public String getPaymentType(){
		return paymentType;
	}

	public void setVendorCode(String vendorCode){
		this.vendorCode = vendorCode;
	}

	public String getVendorCode(){
		return vendorCode;
	}
}