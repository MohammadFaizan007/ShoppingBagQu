package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class ReprintTicketDetails{

	@SerializedName("ReprintPNRDetails")
	private ReprintPNRDetails reprintPNRDetails;

	@SerializedName("ServiceTax")
	private String serviceTax;

	@SerializedName("BookingCustomerDetails")
	private BookingCustomerDetails bookingCustomerDetails;

	@SerializedName("Commission")
	private String commission;

	@SerializedName("CancellationPolicy")
	private String cancellationPolicy;

	public void setReprintPNRDetails(ReprintPNRDetails reprintPNRDetails){
		this.reprintPNRDetails = reprintPNRDetails;
	}

	public ReprintPNRDetails getReprintPNRDetails(){
		return reprintPNRDetails;
	}

	public void setServiceTax(String serviceTax){
		this.serviceTax = serviceTax;
	}

	public String getServiceTax(){
		return serviceTax;
	}

	public void setBookingCustomerDetails(BookingCustomerDetails bookingCustomerDetails){
		this.bookingCustomerDetails = bookingCustomerDetails;
	}

	public BookingCustomerDetails getBookingCustomerDetails(){
		return bookingCustomerDetails;
	}

	public void setCommission(String commission){
		this.commission = commission;
	}

	public String getCommission(){
		return commission;
	}

	public void setCancellationPolicy(String cancellationPolicy){
		this.cancellationPolicy = cancellationPolicy;
	}

	public String getCancellationPolicy(){
		return cancellationPolicy;
	}

	@Override
 	public String toString(){
		return 
			"ReprintTicketDetails{" + 
			"reprintPNRDetails = '" + reprintPNRDetails + '\'' + 
			",serviceTax = '" + serviceTax + '\'' + 
			",bookingCustomerDetails = '" + bookingCustomerDetails + '\'' + 
			",commission = '" + commission + '\'' + 
			",cancellationPolicy = '" + cancellationPolicy + '\'' + 
			"}";
		}
}