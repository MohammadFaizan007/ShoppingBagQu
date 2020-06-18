package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;


public class OriginCitiesItem implements Searchable {

	@SerializedName("OriginName")
	private String originName;

	@SerializedName("OriginId")
	private String originId;

	public void setOriginName(String originName){
		this.originName = originName;
	}

	public String getOriginName(){
		return originName;
	}

	public void setOriginId(String originId){
		this.originId = originId;
	}

	public String getOriginId(){
		return originId;
	}

	@Override
 	public String toString(){
		return 
			"OriginCitiesItem{" + 
			"originName = '" + originName + '\'' + 
			",originId = '" + originId + '\'' + 
			"}";
		}

	@Override
	public String getTitle() {
		return originName;
	}
}