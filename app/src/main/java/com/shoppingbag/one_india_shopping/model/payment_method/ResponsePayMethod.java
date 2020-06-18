package com.shoppingbag.one_india_shopping.model.payment_method;


import com.google.gson.annotations.SerializedName;

public class ResponsePayMethod{

	@SerializedName("data")
	private Data data;

	@SerializedName("response")
	private String response;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePayMethod{" + 
			"data = '" + data + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}
}