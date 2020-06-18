package com.shoppingbag.model.wallet;

import com.google.gson.annotations.SerializedName;

public class ResponseGetKYC{

	@SerializedName("Response")
	private String response;

	@SerializedName("Status")
	private String status;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}