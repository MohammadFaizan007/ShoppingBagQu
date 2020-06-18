package com.shoppingbag.model.response_shopping.product_detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseProductDetail{

	@SerializedName("short_description")
	private String shortDescription;

	@SerializedName("stock_item")
	private StockItem stockItem;

	@SerializedName("color")
	private List<ColorItem> color;

	@SerializedName("size")
	private List<SizeItem> size;

	@SerializedName("price")
	private int price;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("name")
	private String name;

	@SerializedName("media_gallery")
	private List<MediaGalleryItem> mediaGallery;

	@SerializedName("description")
	private String description;

	@SerializedName("Redeem Point")
	private List<RedeemPointItem> redeemPoint;

	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	public void setShortDescription(String shortDescription){
		this.shortDescription = shortDescription;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public void setStockItem(StockItem stockItem){
		this.stockItem = stockItem;
	}

	public StockItem getStockItem(){
		return stockItem;
	}

	public void setColor(List<ColorItem> color){
		this.color = color;
	}

	public List<ColorItem> getColor(){
		return color;
	}

	public void setSize(List<SizeItem> size){
		this.size = size;
	}

	public List<SizeItem> getSize(){
		return size;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return typeId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMediaGallery(List<MediaGalleryItem> mediaGallery){
		this.mediaGallery = mediaGallery;
	}

	public List<MediaGalleryItem> getMediaGallery(){
		return mediaGallery;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setRedeemPoint(List<RedeemPointItem> redeemPoint){
		this.redeemPoint = redeemPoint;
	}

	public List<RedeemPointItem> getRedeemPoint(){
		return redeemPoint;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			"ResponseProductDetail{" + 
			"short_description = '" + shortDescription + '\'' + 
			",stock_item = '" + stockItem + '\'' + 
			",color = '" + color + '\'' + 
			",size = '" + size + '\'' + 
			",price = '" + price + '\'' + 
			",type_id = '" + typeId + '\'' + 
			",name = '" + name + '\'' + 
			",media_gallery = '" + mediaGallery + '\'' + 
			",description = '" + description + '\'' + 
			",redeem Point = '" + redeemPoint + '\'' + 
			",id = '" + id + '\'' + 
			",sku = '" + sku + '\'' + 
			"}";
		}
}