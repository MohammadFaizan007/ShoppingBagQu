package com.shoppingbag.one_india_shopping.model.tracking;

import com.google.gson.annotations.SerializedName;

public class ShipmentTrackActivitiesItem{

	@SerializedName("date")
	private String date;

	@SerializedName("activity")
	private String activity;

	@SerializedName("location")
	private String location;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setActivity(String activity){
		this.activity = activity;
	}

	public String getActivity(){
		return activity;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}
}