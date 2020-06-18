package com.shoppingbag.model.response.bus_response.responsepojo;

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

	public void setBoardingId(Object boardingId){
		this.boardingId = boardingId;
	}

	public Object getBoardingId(){
		return boardingId;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLandMark(String landMark){
		this.landMark = landMark;
	}

	public String getLandMark(){
		return landMark;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setPlace(String place){
		this.place = place;
	}

	public String getPlace(){
		return place;
	}

	@Override
 	public String toString(){
		return 
			"BoardingPoints{" + 
			"boardingId = '" + boardingId + '\'' + 
			",address = '" + address + '\'' + 
			",landMark = '" + landMark + '\'' + 
			",contactNumber = '" + contactNumber + '\'' + 
			",time = '" + time + '\'' + 
			",place = '" + place + '\'' + 
			"}";
		}
}