package com.shoppingbag.one_india_shopping.model.product_description_new;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class OptionItem{

	@SerializedName("image_size_x")
	private int imageSizeX;

	@SerializedName("product_sku")
	private String productSku;

	@SerializedName("image_size_y")
	private int imageSizeY;

	@SerializedName("max_characters")
	private int maxCharacters;

	@SerializedName("values")
	private List<ValuesItem> values;

	@SerializedName("option_id")
	private int optionId;

	@SerializedName("is_require")
	private boolean isRequire;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("sort_order")
	private int sortOrder;

	public void setImageSizeX(int imageSizeX){
		this.imageSizeX = imageSizeX;
	}

	public int getImageSizeX(){
		return imageSizeX;
	}

	public void setProductSku(String productSku){
		this.productSku = productSku;
	}

	public String getProductSku(){
		return productSku;
	}

	public void setImageSizeY(int imageSizeY){
		this.imageSizeY = imageSizeY;
	}

	public int getImageSizeY(){
		return imageSizeY;
	}

	public void setMaxCharacters(int maxCharacters){
		this.maxCharacters = maxCharacters;
	}

	public int getMaxCharacters(){
		return maxCharacters;
	}

	public void setValues(List<ValuesItem> values){
		this.values = values;
	}

	public List<ValuesItem> getValues(){
		return values;
	}

	public void setOptionId(int optionId){
		this.optionId = optionId;
	}

	public int getOptionId(){
		return optionId;
	}

	public void setIsRequire(boolean isRequire){
		this.isRequire = isRequire;
	}

	public boolean isIsRequire(){
		return isRequire;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setSortOrder(int sortOrder){
		this.sortOrder = sortOrder;
	}

	public int getSortOrder(){
		return sortOrder;
	}
}