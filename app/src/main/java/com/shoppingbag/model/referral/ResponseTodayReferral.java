package com.shoppingbag.model.referral;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ResponseTodayReferral{

	@SerializedName("response")
	private String response;

	@SerializedName("Data")
	private List<DataItem> data;

	@SerializedName("message")
	private Object message;

	@SerializedName("totalCount")
	private int totalCount;

	@SerializedName("TransId")
	private int transId;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(Object message){
		this.message = message;
	}

	public Object getMessage(){
		return message;
	}

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setTransId(int transId){
		this.transId = transId;
	}

	public int getTransId(){
		return transId;
	}
}