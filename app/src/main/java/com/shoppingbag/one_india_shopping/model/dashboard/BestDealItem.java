package com.shoppingbag.one_india_shopping.model.dashboard;

import com.google.gson.annotations.SerializedName;

public class BestDealItem{

	@SerializedName("price")
	private double price;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("wislist")
	private String wislist;

	@SerializedName("wishlist_item_id")
	private String wishlistItemId;

	@SerializedName("name")
	private String name;

	@SerializedName("wishlist_id")
	private String wishlistId;

	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("offerprice")
	private String offerprice;

	@SerializedName("gallery")
	private String gallery;

	@SerializedName("status")
	private int status;

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return typeId;
	}

	public void setWislist(String wislist){
		this.wislist = wislist;
	}

	public String getWislist(){
		return wislist;
	}

	public void setWishlistItemId(String wishlistItemId){
		this.wishlistItemId = wishlistItemId;
	}

	public String getWishlistItemId(){
		return wishlistItemId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setWishlistId(String wishlistId){
		this.wishlistId = wishlistId;
	}

	public String getWishlistId(){
		return wishlistId;
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

	public void setOfferprice(String offerprice){
		this.offerprice = offerprice;
	}

	public String getOfferprice(){
		return offerprice;
	}

	public void setGallery(String gallery){
		this.gallery = gallery;
	}

	public String getGallery(){
		return gallery;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"BestDealItem{" + 
			"price = '" + price + '\'' + 
			",type_id = '" + typeId + '\'' + 
			",wislist = '" + wislist + '\'' + 
			",wishlist_item_id = '" + wishlistItemId + '\'' + 
			",name = '" + name + '\'' + 
			",wishlist_id = '" + wishlistId + '\'' + 
			",id = '" + id + '\'' + 
			",sku = '" + sku + '\'' + 
			",offerprice = '" + offerprice + '\'' + 
			",gallery = '" + gallery + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}