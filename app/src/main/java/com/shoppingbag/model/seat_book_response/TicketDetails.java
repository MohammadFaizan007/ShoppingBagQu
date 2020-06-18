package com.shoppingbag.model.seat_book_response;


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

	public void setPNRDetails(PNRDetails pNRDetails){
		this.pNRDetails = pNRDetails;
	}

	public PNRDetails getPNRDetails(){
		return pNRDetails;
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

	public void setConvenienceFee(Object convenienceFee){
		this.convenienceFee = convenienceFee;
	}

	public Object getConvenienceFee(){
		return convenienceFee;
	}
}