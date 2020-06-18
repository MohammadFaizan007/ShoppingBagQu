package com.shoppingbag.model.response.team;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ResponseViewModel{
	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("TransId")
	private int transId;

	@SerializedName("totalCount")
	private int totalCount;

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

	public void setTransId(int transId){
		this.transId = transId;
	}

	public int getTransId(){
		return transId;
	}

	public void settotalCount(int totalCount){
		this.transId = totalCount;
	}

	public int gettotalCount(){
		return totalCount;
	}

	@Override
 	public String toString(){
		return 
			"ResponseViewModel{" + 
			"result = '" + result + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			",transId = '" + transId + '\'' + 
			"}";
		}
}