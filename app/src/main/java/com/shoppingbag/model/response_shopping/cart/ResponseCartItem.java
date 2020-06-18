package com.shoppingbag.model.response_shopping.cart;

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

	@Override
 	public String toString(){
		return 
			"ResponseCartItem{" + 
			"cartitems = '" + cartitems + '\'' + 
			"}";
		}
}