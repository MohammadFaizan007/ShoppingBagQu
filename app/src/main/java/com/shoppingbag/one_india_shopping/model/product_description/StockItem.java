package com.shoppingbag.one_india_shopping.model.product_description;


import com.google.gson.annotations.SerializedName;

public class StockItem{

	@SerializedName("is_in_stock")
	private boolean isInStock;

	@SerializedName("max_sale_qty")
	private int maxSaleQty;

	@SerializedName("min_sale_qty")
	private int minSaleQty;

	@SerializedName("item_id")
	private int itemId;

	@SerializedName("min_qty")
	private int minQty;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("qty")
	private int qty;

	@SerializedName("stock_id")
	private int stockId;

	public void setIsInStock(boolean isInStock){
		this.isInStock = isInStock;
	}

	public boolean isIsInStock(){
		return isInStock;
	}

	public void setMaxSaleQty(int maxSaleQty){
		this.maxSaleQty = maxSaleQty;
	}

	public int getMaxSaleQty(){
		return maxSaleQty;
	}

	public void setMinSaleQty(int minSaleQty){
		this.minSaleQty = minSaleQty;
	}

	public int getMinSaleQty(){
		return minSaleQty;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setMinQty(int minQty){
		this.minQty = minQty;
	}

	public int getMinQty(){
		return minQty;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setQty(int qty){
		this.qty = qty;
	}

	public int getQty(){
		return qty;
	}

	public void setStockId(int stockId){
		this.stockId = stockId;
	}

	public int getStockId(){
		return stockId;
	}

	@Override
 	public String toString(){
		return 
			"StockItem{" + 
			"is_in_stock = '" + isInStock + '\'' + 
			",max_sale_qty = '" + maxSaleQty + '\'' + 
			",min_sale_qty = '" + minSaleQty + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",min_qty = '" + minQty + '\'' + 
			",product_id = '" + productId + '\'' + 
			",qty = '" + qty + '\'' + 
			",stock_id = '" + stockId + '\'' + 
			"}";
		}
}