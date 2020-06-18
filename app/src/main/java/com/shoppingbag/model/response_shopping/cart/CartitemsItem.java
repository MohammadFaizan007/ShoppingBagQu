package com.shoppingbag.model.response_shopping.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartitemsItem{

	@SerializedName("product_type")
	private String productType;

	@SerializedName("item_id")
	private int itemId;

	@SerializedName("price")
	private int price;

	@SerializedName("quote_id")
	private String quoteId;

	@SerializedName("qty")
	private int qty;

	@SerializedName("name")
	private String name;

	@SerializedName("sku")
	private String sku;

	@SerializedName("product_option")
	private List<ProductOptionItem> productOption;

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return productType;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setQuoteId(String quoteId){
		this.quoteId = quoteId;
	}

	public String getQuoteId(){
		return quoteId;
	}

	public void setQty(int qty){
		this.qty = qty;
	}

	public int getQty(){
		return qty;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	public void setProductOption(List<ProductOptionItem> productOption){
		this.productOption = productOption;
	}

	public List<ProductOptionItem> getProductOption(){
		return productOption;
	}

	@Override
 	public String toString(){
		return 
			"CartitemsItem{" + 
			"product_type = '" + productType + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",price = '" + price + '\'' + 
			",quote_id = '" + quoteId + '\'' + 
			",qty = '" + qty + '\'' + 
			",name = '" + name + '\'' + 
			",sku = '" + sku + '\'' + 
			",product_option = '" + productOption + '\'' + 
			"}";
		}
}