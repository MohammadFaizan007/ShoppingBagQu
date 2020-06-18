package com.shoppingbag.model.response_shopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ChildrenDataItem{

	@SerializedName("is_active")
	private boolean isActive;

	@SerializedName("level")
	private int level;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("product_count")
	private int productCount;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private int position;

	@SerializedName("children_data")
	private List<Object> childrenData;

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public int getLevel(){
		return level;
	}

	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	public int getParentId(){
		return parentId;
	}

	public void setProductCount(int productCount){
		this.productCount = productCount;
	}

	public int getProductCount(){
		return productCount;
	}

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

	public void setPosition(int position){
		this.position = position;
	}

	public int getPosition(){
		return position;
	}

	public void setChildrenData(List<Object> childrenData){
		this.childrenData = childrenData;
	}

	public List<Object> getChildrenData(){
		return childrenData;
	}

	@Override
 	public String toString(){
		return 
			"ChildrenDataItem{" + 
			"is_active = '" + isActive + '\'' + 
			",level = '" + level + '\'' + 
			",parent_id = '" + parentId + '\'' + 
			",product_count = '" + productCount + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",position = '" + position + '\'' + 
			",children_data = '" + childrenData + '\'' + 
			"}";
		}
}