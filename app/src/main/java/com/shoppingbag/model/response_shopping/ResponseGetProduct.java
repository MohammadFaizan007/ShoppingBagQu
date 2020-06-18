package com.shoppingbag.model.response_shopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetProduct{

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetProduct{" + 
			"items = '" + items + '\'' + 
			"}";
		}
}