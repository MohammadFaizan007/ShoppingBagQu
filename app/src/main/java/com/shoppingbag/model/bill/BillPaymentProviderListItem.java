package com.shoppingbag.model.bill;

import com.google.gson.annotations.SerializedName;

public class BillPaymentProviderListItem{

	@SerializedName("ProviderName")
	private String providerName;

	@SerializedName("Number")
	private String number;

	@SerializedName("FormatofNumber")
	private String formatofNumber;

	@SerializedName("StateName")
	private String stateName;

	@SerializedName("ImageURL")
	private String imageURL;

	public void setProviderName(String providerName){
		this.providerName = providerName;
	}

	public String getProviderName(){
		return providerName;
	}

	public void setNumber(String number){
		this.number = number;
	}

	public String getNumber(){
		return number;
	}

	public void setFormatofNumber(String formatofNumber){
		this.formatofNumber = formatofNumber;
	}

	public String getFormatofNumber(){
		return formatofNumber;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}

	public String getImageURL(){
		return imageURL;
	}
}