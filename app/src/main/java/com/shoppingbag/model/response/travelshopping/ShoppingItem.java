package com.shoppingbag.model.response.travelshopping;

import com.google.gson.annotations.SerializedName;

public class ShoppingItem{

	@SerializedName("imageurl")
	private String imageurl;

	@SerializedName("link")
	private String link;

	@SerializedName("name")
	private String name;

	public void setImageurl(String imageurl){
		this.imageurl = imageurl;
	}

	public String getImageurl(){
		return imageurl;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"ShoppingItem{" + 
			"imageurl = '" + imageurl + '\'' + 
			",link = '" + link + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}