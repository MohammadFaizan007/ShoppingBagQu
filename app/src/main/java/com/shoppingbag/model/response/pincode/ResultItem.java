package com.shoppingbag.model.response.pincode;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("districtName")
	private String districtName;

	@SerializedName("stateName")
	private String stateName;

	@SerializedName("pinCode")
	private String pinCode;

	@SerializedName("tehsil")
	private String tehsil;

	public void setDistrictName(String districtName){
		this.districtName = districtName;
	}

	public String getDistrictName(){
		return districtName;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setPinCode(String pinCode){
		this.pinCode = pinCode;
	}

	public String getPinCode(){
		return pinCode;
	}

	public void setTehsil(String tehsil){
		this.tehsil = tehsil;
	}

	public String getTehsil(){
		return tehsil;
	}
}