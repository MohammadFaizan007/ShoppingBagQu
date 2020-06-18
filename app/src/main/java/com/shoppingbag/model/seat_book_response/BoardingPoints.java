package com.shoppingbag.model.seat_book_response;


import com.google.gson.annotations.SerializedName;


public class BoardingPoints{

	@SerializedName("Address")
	private String address;

	@SerializedName("BoardingId")
	private Object boardingId;

	@SerializedName("LandMark")
	private String landMark;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Time")
	private String time;

	@SerializedName("Place")
	private String place;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setBoardingId(Object boardingId){
		this.boardingId = boardingId;
	}

	public Object getBoardingId(){
		return boardingId;
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
}