package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookOutput{

	@SerializedName("Blockingdetails")
	private Object blockingdetails;

	@SerializedName("FlightTicketDetails")
	private List<FlightTicketDetailsItem> flightTicketDetails;

	public void setBlockingdetails(Object blockingdetails){
		this.blockingdetails = blockingdetails;
	}

	public Object getBlockingdetails(){
		return blockingdetails;
	}

	public void setFlightTicketDetails(List<FlightTicketDetailsItem> flightTicketDetails){
		this.flightTicketDetails = flightTicketDetails;
	}

	public List<FlightTicketDetailsItem> getFlightTicketDetails(){
		return flightTicketDetails;
	}
}