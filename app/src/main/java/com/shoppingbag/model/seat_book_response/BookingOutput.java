package com.shoppingbag.model.seat_book_response;


import com.google.gson.annotations.SerializedName;


public class BookingOutput{

	@SerializedName("TicketDetails")
	private TicketDetails ticketDetails;

	public void setTicketDetails(TicketDetails ticketDetails){
		this.ticketDetails = ticketDetails;
	}

	public TicketDetails getTicketDetails(){
		return ticketDetails;
	}
}