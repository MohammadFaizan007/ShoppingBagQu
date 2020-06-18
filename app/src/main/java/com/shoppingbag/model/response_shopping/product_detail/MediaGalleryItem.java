package com.shoppingbag.model.response_shopping.product_detail;

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

	@Override
 	public String toString(){
		return 
			"MediaGalleryItem{" + 
			"file = '" + file + '\'' + 
			"}";
		}
}