package com.shoppingbag.one_india_shopping.model.payment_method;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Data{

	@SerializedName("shipping_amount")
	private double shippingAmount;

	@SerializedName("price")
	private double price;

	@SerializedName("freeshipping")
	private Freeshipping freeshipping;

	@SerializedName("grand_total")
	private double grandTotal;

	@SerializedName("payment_method")
	private List<PaymentMethodItem> paymentMethod;

	@SerializedName("flatrate")
	private Flatrate flatrate;

	public void setShippingAmount(double shippingAmount){
		this.shippingAmount = shippingAmount;
	}

	public double getShippingAmount(){
		return shippingAmount;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setFreeshipping(Freeshipping freeshipping){
		this.freeshipping = freeshipping;
	}

	public Freeshipping getFreeshipping(){
		return freeshipping;
	}

	public void setGrandTotal(double grandTotal){
		this.grandTotal = grandTotal;
	}

	public double getGrandTotal(){
		return grandTotal;
	}

	public void setPaymentMethod(List<PaymentMethodItem> paymentMethod){
		this.paymentMethod = paymentMethod;
	}

	public List<PaymentMethodItem> getPaymentMethod(){
		return paymentMethod;
	}

	public void setFlatrate(Flatrate flatrate){
		this.flatrate = flatrate;
	}

	public Flatrate getFlatrate(){
		return flatrate;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"shipping_amount = '" + shippingAmount + '\'' + 
			",price = '" + price + '\'' + 
			",freeshipping = '" + freeshipping + '\'' + 
			",grand_total = '" + grandTotal + '\'' + 
			",payment_method = '" + paymentMethod + '\'' + 
			",flatrate = '" + flatrate + '\'' + 
			"}";
		}
}