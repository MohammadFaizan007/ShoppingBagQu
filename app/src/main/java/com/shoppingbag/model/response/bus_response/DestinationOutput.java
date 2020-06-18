package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DestinationOutput {

	@SerializedName("DestinationCities")
	private List<DestinationCitiesItem> destinationCities;

	public void setDestinationCities(List<DestinationCitiesItem> destinationCities){
		this.destinationCities = destinationCities;
	}

	public List<DestinationCitiesItem> getDestinationCities(){
		return destinationCities;
	}

	@Override
 	public String toString(){
		return 
			"DestinationOutput{" + 
			"destinationCities = '" + destinationCities + '\'' + 
			"}";
		}
}