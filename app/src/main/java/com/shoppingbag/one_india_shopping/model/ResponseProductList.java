package com.shoppingbag.one_india_shopping.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseProductList{

	@SerializedName("product_list")
	private List<ProductListItem> productList;

	public void setProductList(List<ProductListItem> productList){
		this.productList = productList;
	}

	public List<ProductListItem> getProductList(){
		return productList;
	}
}