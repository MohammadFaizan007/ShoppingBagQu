package com.shoppingbag.one_india_shopping.model.payment_method;


import com.google.gson.annotations.SerializedName;

public class Freeshipping{

	@SerializedName("code")
	private String code;

	@SerializedName("title")
	private String title;

	@SerializedName("value")
	private double value;

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

	public void setValue(int value){
		this.value = value;
	}

	public double getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"Freeshipping{" + 
			"code = '" + code + '\'' + 
			",title = '" + title + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}