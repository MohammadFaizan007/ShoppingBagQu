package com.shoppingbag.one_india_shopping.model.cartitem;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CartitemsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("product_type")
	private String productType;

	@SerializedName("item_id")
	private int itemId;

	@SerializedName("special_price")
	private String specialPrice;

	@SerializedName("price")
	private double price;

	@SerializedName("quote_id")
	private String quoteId;

	@SerializedName("qty")
	private int qty;

	@SerializedName("name")
	private String name;

	@SerializedName("sku")
	private String sku;

	@SerializedName("stock")
	private String stock;

	@SerializedName("stock_quantity")
	private int stockQuantity;

	public int getCashback() {
		return cashback;
	}

	public void setCashback(int cashback) {
		this.cashback = cashback;
	}

	@SerializedName("cashback")
	private int cashback;

	@SerializedName("product_option")
	private List<ProductOptionItem> productOption;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

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

	public void setSpecialPrice(String specialPrice){
		this.specialPrice = specialPrice;
	}

	public String getSpecialPrice(){
		return specialPrice;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
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

	public void setStock(String stock){
		this.stock = stock;
	}

	public String getStock(){
		return stock;
	}

	public void setStockQuantity(int stockQuantity){
		this.stockQuantity = stockQuantity;
	}

	public int getStockQuantity(){
		return stockQuantity;
	}

	public void setProductOption(List<ProductOptionItem> productOption){
		this.productOption = productOption;
	}

	public List<ProductOptionItem> getProductOption(){
		return productOption;
	}
}