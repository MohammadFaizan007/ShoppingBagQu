package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

public class BoardingPoints{

	@SerializedName("BoardingId")
	private Object boardingId;

	@SerializedName("Address")
	private String address;

	@SerializedName("LandMark")
	private String landMark;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Time")
	private String time;

	@SerializedName("Place")
	private String place;

	public Object getBoardingId(){
		return boardingId;
	}

	public String getAddress(){
		return address;
	}

	public String getLandMark(){
		return landMark;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public String getTime(){
		return time;
	}

	public String getPlace(){
		return place;
	}
}