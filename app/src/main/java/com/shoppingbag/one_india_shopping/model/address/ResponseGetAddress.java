package com.shoppingbag.one_india_shopping.model.address;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetAddress{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("response")
	private String response;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setResponse(String message){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}
}