package com.shoppingbag.one_india_shopping.model.product_description;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Option{

	@SerializedName("Size")
	private List<String> size;

	@SerializedName("Color")
	private List<String> color;

	public void setSize(List<String> size){
		this.size = size;
	}

	public List<String> getSize(){
		return size;
	}

	public void setColor(List<String> color){
		this.color = color;
	}

	public List<String> getColor(){
		return color;
	}

	@Override
 	public String toString(){
		return 
			"Option{" + 
			"size = '" + size + '\'' + 
			",color = '" + color + '\'' + 
			"}";
		}
}