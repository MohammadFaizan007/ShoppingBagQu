package com.shoppingbag.model.support;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetSupportType{

	@SerializedName("Item")
	private List<ItemItem> item;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private Object message;

	@SerializedName("totalCount")
	private int totalCount;

	@SerializedName("TransId")
	private int transId;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
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