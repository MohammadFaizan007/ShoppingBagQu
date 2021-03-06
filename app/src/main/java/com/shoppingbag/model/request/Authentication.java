package com.shoppingbag.model.request;

import com.google.gson.annotations.SerializedName;

public class Authentication{

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("Password")
	private String password;

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	@Override
 	public String toString(){
		return 
			"Authentication{" + 
			"loginId = '" + loginId + '\'' + 
			",password = '" + password + '\'' + 
			"}";
		}
}