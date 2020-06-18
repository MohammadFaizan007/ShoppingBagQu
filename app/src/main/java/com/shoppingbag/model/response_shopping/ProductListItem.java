package com.shoppingbag.model.response_shopping;

import com.google.gson.annotations.SerializedName;

public class ProductListItem{

	@SerializedName("price")
	private String price;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("gallery")
	private String gallery;

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return typeId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	public void setGallery(String gallery){
		this.gallery = gallery;
	}

	public String getGallery(){
		return gallery;
	}

	@Override
 	public String toString(){
		return 
			"ProductListItem{" + 
			"price = '" + price + '\'' + 
			",type_id = '" + typeId + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",sku = '" + sku + '\'' + 
			",gallery = '" + gallery + '\'' + 
			"}";
		}
}