package com.shoppingbag.one_india_shopping.model.cartitem;


import com.google.gson.annotations.SerializedName;


public class ProductOptionItem{

	@SerializedName("option_value")
	private String optionValue;

	@SerializedName("option_id")
	private String optionId;

	public void setOptionValue(String optionValue){
		this.optionValue = optionValue;
	}

	public String getOptionValue(){
		return optionValue;
	}

	public void setOptionId(String optionId){
		this.optionId = optionId;
	}

	public String getOptionId(){
		return optionId;
	}
}