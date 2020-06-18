package com.shoppingbag.model.request_shopping.cart_update;

import com.google.gson.annotations.SerializedName;

public class CartItem{

	@SerializedName("qty")
	private int qty;

	@SerializedName("quote_id")
	private String quoteId;

	@SerializedName("sku")
	private String sku;

	public void setQty(int qty){
		this.qty = qty;
	}

	public int getQty(){
		return qty;
	}

	public void setQuoteId(String quoteId){
		this.quoteId = quoteId;
	}

	public String getQuoteId(){
		return quoteId;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	@Override
 	public String toString(){
		return 
			"CartItem{" + 
			"qty = '" + qty + '\'' + 
			",quote_id = '" + quoteId + '\'' + 
			",sku = '" + sku + '\'' + 
			"}";
		}
}