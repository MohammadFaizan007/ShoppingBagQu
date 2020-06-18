package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class RequestGetSeatBlock{

	@SerializedName("UserTrackId")
	private String userTrackId;

	@SerializedName("FK_MemID")
	private String fKMemID;

	@SerializedName("BookingInput")
	private BookingInput bookingInput;

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}

	public void setFKMemID(String fKMemID){
		this.fKMemID = fKMemID;
	}

	public String getFKMemID(){
		return fKMemID;
	}

	public void setBookingInput(BookingInput bookingInput){
		this.bookingInput = bookingInput;
	}

	public BookingInput getBookingInput(){
		return bookingInput;
	}

	@Override
 	public String toString(){
		return 
			"RequestGetSeatBlock{" + 
			"userTrackId = '" + userTrackId + '\'' + 
			",fK_MemID = '" + fKMemID + '\'' + 
			",bookingInput = '" + bookingInput + '\'' + 
			"}";
		}
}