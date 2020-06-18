package com.shoppingbag.one_india_shopping.model.myorderdetails;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("firstname")
	private String firstname;

	@SerializedName("item_id")
	private String itemId;

	@SerializedName("city")
	private String city;

	@SerializedName("discount_amount")
	private String discountAmount;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("price")
	private String price;

	@SerializedName("street")
	private String street;

	@SerializedName("qty")
	private String qty;

	@SerializedName("name")
	private String name;

	@SerializedName("state")
	private String state;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setDiscountAmount(String discountAmount){
		this.discountAmount = discountAmount;
	}

	public String getDiscountAmount(){
		return discountAmount;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return postcode;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setQty(String qty){
		this.qty = qty;
	}

	public String getQty(){
		return qty;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",firstname = '" + firstname + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",city = '" + city + '\'' + 
			",discount_amount = '" + discountAmount + '\'' + 
			",postcode = '" + postcode + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",price = '" + price + '\'' + 
			",street = '" + street + '\'' + 
			",qty = '" + qty + '\'' + 
			",name = '" + name + '\'' + 
			",state = '" + state + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}