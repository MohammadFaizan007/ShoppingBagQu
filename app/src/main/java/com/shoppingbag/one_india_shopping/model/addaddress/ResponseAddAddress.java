package com.shoppingbag.one_india_shopping.model.addaddress;

import com.google.gson.annotations.SerializedName;

public class ResponseAddAddress{

	@SerializedName("data")
	private String data;

	@SerializedName("response")
	private String response;

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}
}