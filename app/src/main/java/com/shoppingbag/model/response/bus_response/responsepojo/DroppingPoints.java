package com.shoppingbag.model.response.bus_response.responsepojo;

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

	public void setDroppingId(Object droppingId){
		this.droppingId = droppingId;
	}

	public Object getDroppingId(){
		return droppingId;
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
			"DroppingPoints{" + 
			"address = '" + address + '\'' + 
			",landMark = '" + landMark + '\'' + 
			",contactNumber = '" + contactNumber + '\'' + 
			",time = '" + time + '\'' + 
			",droppingId = '" + droppingId + '\'' + 
			",place = '" + place + '\'' + 
			"}";
		}
}