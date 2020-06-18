package com.shoppingbag.one_india_shopping.model.dashboard;

import com.google.gson.annotations.SerializedName;

public class BestSellerItemItem{

	@SerializedName("price")
	private String price;

	@SerializedName("wislist")
	private String wislist;

	@SerializedName("wishlist_item_id")
	private String wishlistItemId;

	@SerializedName("name")
	private String name;

	@SerializedName("wishlist_id")
	private String wishlistId;

	@SerializedName("id")
	private String id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("value")
	private String value;

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
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
			"BestSellerItemItem{" + 
			"price = '" + price + '\'' + 
			",wislist = '" + wislist + '\'' + 
			",wishlist_item_id = '" + wishlistItemId + '\'' + 
			",name = '" + name + '\'' + 
			",wishlist_id = '" + wishlistId + '\'' + 
			",id = '" + id + '\'' + 
			",sku = '" + sku + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}