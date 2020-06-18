package com.shoppingbag.one_india_shopping.model.payment_method;


import com.google.gson.annotations.SerializedName;

public class PaymentMethodItem{

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
			"PaymentMethodItem{" + 
			"code = '" + code + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}