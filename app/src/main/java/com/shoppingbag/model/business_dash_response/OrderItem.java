package com.shoppingbag.model.business_dash_response;


import com.google.gson.annotations.SerializedName;


public class OrderItem{

	@SerializedName("Type")
	private String type;

	@SerializedName("ActionType")
	private String actionType;

	@SerializedName("Amount")
	private int amount;

	@SerializedName("OrderId")
	private String orderId;

	@SerializedName("OrderDate")
	private String orderDate;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setActionType(String actionType){
		this.actionType = actionType;
	}

	public String getActionType(){
		return actionType;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setOrderDate(String orderDate){
		this.orderDate = orderDate;
	}

	public String getOrderDate(){
		return orderDate;
	}
}