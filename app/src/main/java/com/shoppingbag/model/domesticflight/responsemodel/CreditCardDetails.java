package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class CreditCardDetails{

	@SerializedName("CardNumber")
	private Object cardNumber;

	@SerializedName("CardType")
	private Object cardType;

	public void setCardNumber(Object cardNumber){
		this.cardNumber = cardNumber;
	}

	public Object getCardNumber(){
		return cardNumber;
	}

	public void setCardType(Object cardType){
		this.cardType = cardType;
	}

	public Object getCardType(){
		return cardType;
	}
}