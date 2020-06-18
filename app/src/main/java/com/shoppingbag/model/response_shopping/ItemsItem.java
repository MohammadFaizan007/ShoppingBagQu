package com.shoppingbag.model.response_shopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("price")
	private int price;

	@SerializedName("media_gallery_entries")
	private List<MediaGalleryEntriesItem> mediaGalleryEntries;

	@SerializedName("name")
	private String name;

	@SerializedName("sku")
	private String sku;

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setMediaGalleryEntries(List<MediaGalleryEntriesItem> mediaGalleryEntries){
		this.mediaGalleryEntries = mediaGalleryEntries;
	}

	public List<MediaGalleryEntriesItem> getMediaGalleryEntries(){
		return mediaGalleryEntries;
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

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"price = '" + price + '\'' + 
			",media_gallery_entries = '" + mediaGalleryEntries + '\'' + 
			",name = '" + name + '\'' + 
			",sku = '" + sku + '\'' + 
			"}";
		}
}