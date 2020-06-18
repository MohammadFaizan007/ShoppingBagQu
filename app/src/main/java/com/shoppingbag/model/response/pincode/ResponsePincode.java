package com.shoppingbag.model.response.pincode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePincode{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("response")
	private String response;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}
}