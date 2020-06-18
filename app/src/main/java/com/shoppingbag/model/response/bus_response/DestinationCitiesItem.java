package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;

public class DestinationCitiesItem implements Searchable {

	@SerializedName("DestinationName")
	private String destinationName;

	@SerializedName("DestinationId")
	private String destinationId;

	public void setDestinationName(String destinationName){
		this.destinationName = destinationName;
	}

	public String getDestinationName(){
		return destinationName;
	}

	public void setDestinationId(String destinationId){
		this.destinationId = destinationId;
	}

	public String getDestinationId(){
		return destinationId;
	}

	@Override
 	public String toString(){
		return 
			"DestinationCitiesItem{" + 
			"destinationName = '" + destinationName + '\'' + 
			",destinationId = '" + destinationId + '\'' + 
			"}";
		}

	@Override
	public String getTitle() {
		return destinationName;
	}
}