package com.shoppingbag.one_india_shopping.model.product_description_new;


import com.google.gson.annotations.SerializedName;


public class MediaGalleryItem{

	@SerializedName("file")
	private String file;

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}
}