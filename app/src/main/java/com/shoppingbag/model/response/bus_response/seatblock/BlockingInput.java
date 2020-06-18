package com.shoppingbag.model.response.bus_response.seatblock;

import com.google.gson.annotations.SerializedName;

public class BlockingInput{

	@SerializedName("BookingDetails")
	private BookingDetails bookingDetails;

	@SerializedName("BookingCustomerDetails")
	private BookingCustomerDetails bookingCustomerDetails;

	public void setBookingDetails(BookingDetails bookingDetails){
		this.bookingDetails = bookingDetails;
	}

	public BookingDetails getBookingDetails(){
		return bookingDetails;
	}

	public void setBookingCustomerDetails(BookingCustomerDetails bookingCustomerDetails){
		this.bookingCustomerDetails = bookingCustomerDetails;
	}

	public BookingCustomerDetails getBookingCustomerDetails(){
		return bookingCustomerDetails;
	}
}