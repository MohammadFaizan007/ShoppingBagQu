package com.shoppingbag.one_india_shopping.model.dashboard;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Cat{

	@SerializedName("category_list")
	private List<CategoryListItem> categoryList;

	public void setCategoryList(List<CategoryListItem> categoryList){
		this.categoryList = categoryList;
	}

	public List<CategoryListItem> getCategoryList(){
		return categoryList;
	}

	@Override
 	public String toString(){
		return 
			"Cat{" + 
			"category_list = '" + categoryList + '\'' + 
			"}";
		}
}