package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class RequestGetReprint{

	@SerializedName("ReprintInput")
	private ReprintInput reprintInput;

	public void setReprintInput(ReprintInput reprintInput){
		this.reprintInput = reprintInput;
	}

	public ReprintInput getReprintInput(){
		return reprintInput;
	}

	@Override
 	public String toString(){
		return 
			"RequestGetReprint{" + 
			"reprintInput = '" + reprintInput + '\'' + 
			"}";
		}
}