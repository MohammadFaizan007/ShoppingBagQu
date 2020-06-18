package com.shoppingbag.model.wallet;

import com.google.gson.annotations.SerializedName;

public class ResponseWalletBalance{

	@SerializedName("result")
	private Result result;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;


	@SerializedName("TransId")
	private int transId;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
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
}