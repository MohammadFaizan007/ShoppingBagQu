package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OriginOutput{

	@SerializedName("OriginCities")
	private List<OriginCitiesItem> originCities;

	public void setOriginCities(List<OriginCitiesItem> originCities){
		this.originCities = originCities;
	}

	public List<OriginCitiesItem> getOriginCities(){
		return originCities;
	}

	@Override
 	public String toString(){
		return 
			"OriginOutput{" + 
			"originCities = '" + originCities + '\'' + 
			"}";
		}
}