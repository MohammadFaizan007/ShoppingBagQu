package com.shoppingbag.wallet.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WalletRequestModel{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("TransId")
	private int transId;

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

	@Override
 	public String toString(){
		return 
			"WalletRequestModel{" + 
			"result = '" + result + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			",transId = '" + transId + '\'' + 
			"}";
		}
}