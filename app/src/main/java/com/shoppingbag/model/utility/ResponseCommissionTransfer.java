package com.shoppingbag.model.utility;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCommissionTransfer{

	@SerializedName("result")
	private String result;

	@SerializedName("response")
	private String response;

	@SerializedName("list")
	private List<ListItem> list;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setList(List<ListItem> list){
		this.list = list;
	}

	public List<ListItem> getList(){
		return list;
	}
}