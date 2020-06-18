package com.shoppingbag.one_india_shopping.model.token;

import com.google.gson.annotations.SerializedName;

public class ResponseToken{

	@SerializedName("q_id")
	private String qId;

	@SerializedName("token")
	private String token;

	public void setQId(String qId){
		this.qId = qId;
	}

	public String getQId(){
		return qId;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResponseToken{" + 
			"q_id = '" + qId + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}