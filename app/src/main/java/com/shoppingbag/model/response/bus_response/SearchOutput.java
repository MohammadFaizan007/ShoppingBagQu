package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchOutput{

	@SerializedName("AvailableBuses")
	private List<AvailableBusesItem> availableBuses;

	public void setAvailableBuses(List<AvailableBusesItem> availableBuses){
		this.availableBuses = availableBuses;
	}

	public List<AvailableBusesItem> getAvailableBuses(){
		return availableBuses;
	}

	@Override
 	public String toString(){
		return 
			"SearchOutput{" + 
			"availableBuses = '" + availableBuses + '\'' + 
			"}";
		}
}