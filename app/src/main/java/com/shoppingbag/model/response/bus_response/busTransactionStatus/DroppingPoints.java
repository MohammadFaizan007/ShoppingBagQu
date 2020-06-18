package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

public class DroppingPoints{

	@SerializedName("Address")
	private String address;

	@SerializedName("LandMark")
	private String landMark;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Time")
	private String time;

	@SerializedName("DroppingId")
	private Object droppingId;

	@SerializedName("Place")
	private String place;

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

	public Object getDroppingId(){
		return droppingId;
	}

	public String getPlace(){
		return place;
	}
}