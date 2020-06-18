package com.shoppingbag.model.response.bus_response.bookingHistory;

import com.google.gson.annotations.SerializedName;

public class ResponseBusBookings{

	@SerializedName("FailureRemarks")
	private Object failureRemarks;

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("BookedHistoryOutput")
	private BookedHistoryOutput bookedHistoryOutput;

	public Object getFailureRemarks(){
		return failureRemarks;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	public BookedHistoryOutput getBookedHistoryOutput(){
		return bookedHistoryOutput;
	}
}