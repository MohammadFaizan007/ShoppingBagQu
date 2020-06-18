package com.shoppingbag.one_india_shopping.model.cartitem;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ResponseCartItem{

	@SerializedName("cartitems")
	private List<CartitemsItem> cartitems;

	public void setCartitems(List<CartitemsItem> cartitems){
		this.cartitems = cartitems;
	}

	public List<CartitemsItem> getCartitems(){
		return cartitems;
	}
}