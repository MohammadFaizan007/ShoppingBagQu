package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

public class TicketDetails{

	@SerializedName("PNRDetails")
	private PNRDetails pNRDetails;

	@SerializedName("ServiceTax")
	private String serviceTax;

	@SerializedName("BookingCustomerDetails")
	private BookingCustomerDetails bookingCustomerDetails;

	@SerializedName("Commission")
	private String commission;

	@SerializedName("CancellationPolicy")
	private String cancellationPolicy;

	@SerializedName("ConvenienceFee")
	private Object convenienceFee;

	public PNRDetails getPNRDetails(){
		return pNRDetails;
	}

	public String getServiceTax(){
		return serviceTax;
	}

	public BookingCustomerDetails getBookingCustomerDetails(){
		return bookingCustomerDetails;
	}

	public String getCommission(){
		return commission;
	}

	public String getCancellationPolicy(){
		return cancellationPolicy;
	}

	public Object getConvenienceFee(){
		return convenienceFee;
	}
}