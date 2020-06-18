package com.shoppingbag.model.request_shopping.cart_update;

import com.google.gson.annotations.SerializedName;

public class RequestCartUpdate{

	@SerializedName("member_id")
	private int memberId;

	@SerializedName("item_id")
	private int itemId;

	@SerializedName("cartItem")
	private CartItem cartItem;

	public void setMemberId(int memberId){
		this.memberId = memberId;
	}

	public int getMemberId(){
		return memberId;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setCartItem(CartItem cartItem){
		this.cartItem = cartItem;
	}

	public CartItem getCartItem(){
		return cartItem;
	}

	@Override
 	public String toString(){
		return 
			"RequestCartUpdate{" + 
			"member_id = '" + memberId + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",cartItem = '" + cartItem + '\'' + 
			"}";
		}
}