package com.shoppingbag.model.seat_book_response;


import com.google.gson.annotations.SerializedName;


public class ResponseSeatBooked{

	@SerializedName("FailureRemarks")
	private Object failureRemarks;

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("UserTrackId")
	private String userTrackId;

	@SerializedName("BookingOutput")
	private BookingOutput bookingOutput;

	public void setFailureRemarks(Object failureRemarks){
		this.failureRemarks = failureRemarks;
	}

	public Object getFailureRemarks(){
		return failureRemarks;
	}

	public void setResponseStatus(String responseStatus){
		this.responseStatus = responseStatus;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}

	public void setBookingOutput(BookingOutput bookingOutput){
		this.bookingOutput = bookingOutput;
	}

	public BookingOutput getBookingOutput(){
		return bookingOutput;
	}
}