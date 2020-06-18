package com.shoppingbag.one_india_shopping.model.dashboard;

import com.google.gson.annotations.SerializedName;

public class CategoryListItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public boolean isStatus() {
		return status;
	}

	@SerializedName("status")
	private boolean status;





	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CategoryListItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}