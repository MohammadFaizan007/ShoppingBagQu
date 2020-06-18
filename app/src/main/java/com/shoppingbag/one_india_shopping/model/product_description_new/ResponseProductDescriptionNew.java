package com.shoppingbag.one_india_shopping.model.product_description_new;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ResponseProductDescriptionNew {

	@SerializedName("short_description")
	private String shortDescription;

	@SerializedName("stock_item")
	private StockItem stockItem;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("price")
	private int price;

	public int getCashback() {
		return cashback;
	}

	public void setCashback(int cashback) {
		this.cashback = cashback;
	}

	@SerializedName("cashback")
	private int cashback;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("name")
	private String name;

	@SerializedName("media_gallery")
	private List<MediaGalleryItem> mediaGallery;

	@SerializedName("description")
	private Object description;

	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("offerprice")
	private String offerprice;

	@SerializedName("option")
	private List<OptionItem> option;

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

	public void setCatName(String catName){
		this.catName = catName;
	}

	public String getCatName(){
		return catName;
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

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
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

	public void setOfferprice(String offerprice){
		this.offerprice = offerprice;
	}

	public String getOfferprice(){
		return offerprice;
	}

	public void setOption(List<OptionItem> option){
		this.option = option;
	}

	public List<OptionItem> getOption(){
		return option;
	}
}