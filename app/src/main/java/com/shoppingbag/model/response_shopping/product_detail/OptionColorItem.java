package com.shoppingbag.model.response_shopping.product_detail;

import com.google.gson.annotations.SerializedName;

public class OptionColorItem{

	@SerializedName("price")
	private int price;

	@SerializedName("price_type")
	private String priceType;

	@SerializedName("option_type_id")
	private int optionTypeId;

	@SerializedName("title")
	private String title;

	@SerializedName("sort_order")
	private int sortOrder;

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setPriceType(String priceType){
		this.priceType = priceType;
	}

	public String getPriceType(){
		return priceType;
	}

	public void setOptionTypeId(int optionTypeId){
		this.optionTypeId = optionTypeId;
	}

	public int getOptionTypeId(){
		return optionTypeId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setSortOrder(int sortOrder){
		this.sortOrder = sortOrder;
	}

	public int getSortOrder(){
		return sortOrder;
	}

	@Override
 	public String toString(){
		return 
			"OptionColorItem{" + 
			"price = '" + price + '\'' + 
			",price_type = '" + priceType + '\'' + 
			",option_type_id = '" + optionTypeId + '\'' + 
			",title = '" + title + '\'' + 
			",sort_order = '" + sortOrder + '\'' + 
			"}";
		}
}