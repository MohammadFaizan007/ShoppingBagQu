package com.shoppingbag.one_india_shopping.model.product_description;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductionDespModel{

	@SerializedName("short_description")
	private String shortDescription;

	@SerializedName("gross")
	private Object gross;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("media_gallery")
	private List<MediaGalleryItem> mediaGallery;

	@SerializedName("description")
	private String description;

	@SerializedName("pieces")
	private Object pieces;

	@SerializedName("stock_item")
	private StockItem stockItem;

	@SerializedName("price")
	private double price;

	@SerializedName("offerprice")
	private String offerprice;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("net")
	private int net;

	@SerializedName("option")
	private Option option;

	public void setShortDescription(String shortDescription){
		this.shortDescription = shortDescription;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public void setGross(Object gross){
		this.gross = gross;
	}

	public Object getGross(){
		return gross;
	}

	public void setCatName(String catName){
		this.catName = catName;
	}

	public String getCatName(){
		return catName;
	}

	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return typeId;
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

	public void setPieces(Object pieces){
		this.pieces = pieces;
	}

	public Object getPieces(){
		return pieces;
	}

	public void setStockItem(StockItem stockItem){
		this.stockItem = stockItem;
	}

	public StockItem getStockItem(){
		return stockItem;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setofferprice(String offerprice){
		this.offerprice = offerprice;
	}

	public String getofferprice(){
		return offerprice;
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

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	public void setNet(int net){
		this.net = net;
	}

	public int getNet(){
		return net;
	}

	public void setOption(Option option){
		this.option = option;
	}

	public Option getOption(){
		return option;
	}

	@Override
 	public String toString(){
		return 
			"ProductionDespModel{" + 
			"short_description = '" + shortDescription + '\'' + 
			",gross = '" + gross + '\'' + 
			",cat_name = '" + catName + '\'' + 
			",type_id = '" + typeId + '\'' + 
			",media_gallery = '" + mediaGallery + '\'' + 
			",description = '" + description + '\'' + 
			",pieces = '" + pieces + '\'' + 
			",stock_item = '" + stockItem + '\'' + 
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",sku = '" + sku + '\'' + 
			",net = '" + net + '\'' + 
			",option = '" + option + '\'' + 
			"}";
		}
}