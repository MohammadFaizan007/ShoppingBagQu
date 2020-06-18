package com.shoppingbag.one_india_shopping.model.myproductdescription;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Option{

	@SerializedName("Size")
	private List<SizeItem> size;

	@SerializedName("Color")
	private List<ColorItem> color;

	public void setSize(List<SizeItem> size){
		this.size = size;
	}

	public List<SizeItem> getSize(){
		return size;
	}

	public void setColor(List<ColorItem> color){
		this.color = color;
	}

	public List<ColorItem> getColor(){
		return color;
	}

	@Override
 	public String toString(){
		return 
			"Option{" + 
			"size = '" + size + '\'' + 
			"}";
		}
}