package com.shoppingbag.model.bankdetails;


import com.google.gson.annotations.SerializedName;


public class ResponseBankDetails{

	@SerializedName("result")
	private Result result;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

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

	@Override
 	public String toString(){
		return 
			"ResponseBankDetails{" + 
			"result = '" + result + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}