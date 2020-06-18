package com.shoppingbag.model.support;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetAllSupport{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

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

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}