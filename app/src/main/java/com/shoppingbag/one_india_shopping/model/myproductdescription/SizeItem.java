package com.shoppingbag.one_india_shopping.model.myproductdescription;

import com.google.gson.annotations.SerializedName;

public class SizeItem{

	@SerializedName("option_id")
	private int optionId;

	@SerializedName("value_id")
	private String valueId;

	@SerializedName("value")
	private String value;

	public void setOptionId(int optionId){
		this.optionId = optionId;
	}

	public int getOptionId(){
		return optionId;
	}

	public void setValueId(String valueId){
		this.valueId = valueId;
	}

	public String getValueId(){
		return valueId;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"SizeItem{" + 
			"option_id = '" + optionId + '\'' + 
			",value_id = '" + valueId + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}