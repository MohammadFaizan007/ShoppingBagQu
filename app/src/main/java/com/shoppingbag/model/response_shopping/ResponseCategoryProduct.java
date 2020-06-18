package com.shoppingbag.model.response_shopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCategoryProduct{

	@SerializedName("product_list")
	private List<ProductListItem> productList;

	public void setProductList(List<ProductListItem> productList){
		this.productList = productList;
	}

	public List<ProductListItem> getProductList(){
		return productList;
	}

	@Override
 	public String toString(){
		return 
			"ResponseCategoryProduct{" + 
			"product_list = '" + productList + '\'' + 
			"}";
		}
}