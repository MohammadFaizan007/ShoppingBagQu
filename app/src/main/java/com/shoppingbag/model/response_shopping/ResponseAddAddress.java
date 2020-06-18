package com.shoppingbag.model.response_shopping;

import com.google.gson.annotations.SerializedName;

public class ResponseAddAddress{

	@SerializedName("code")
	private String code;

	@SerializedName("title")
	private String title;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAddAddress{" + 
			"code = '" + code + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}