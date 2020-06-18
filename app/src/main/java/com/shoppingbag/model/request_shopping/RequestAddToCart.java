package com.shoppingbag.model.request_shopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RequestAddToCart{

	@SerializedName("custom_options")
	private List<CustomOptionsItem> customOptions;

	@SerializedName("qty")
	private int qty;

	@SerializedName("quote_id")
	private String quoteId;

	@SerializedName("sku")
	private String sku;

	public void setCustomOptions(List<CustomOptionsItem> customOptions){
		this.customOptions = customOptions;
	}

	public List<CustomOptionsItem> getCustomOptions(){
		return customOptions;
	}

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
			"RequestAddToCart{" + 
			"custom_options = '" + customOptions + '\'' + 
			",qty = '" + qty + '\'' + 
			",quote_id = '" + quoteId + '\'' + 
			",sku = '" + sku + '\'' + 
			"}";
		}
}