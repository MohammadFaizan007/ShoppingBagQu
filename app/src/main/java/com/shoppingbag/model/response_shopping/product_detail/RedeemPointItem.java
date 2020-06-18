package com.shoppingbag.model.response_shopping.product_detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RedeemPointItem{

	@SerializedName("option_Redeem Point")
	private List<OptionRedeemPointItem> optionRedeemPoint;

	public void setOptionRedeemPoint(List<OptionRedeemPointItem> optionRedeemPoint){
		this.optionRedeemPoint = optionRedeemPoint;
	}

	public List<OptionRedeemPointItem> getOptionRedeemPoint(){
		return optionRedeemPoint;
	}

	@Override
 	public String toString(){
		return 
			"RedeemPointItem{" + 
			"option_Redeem Point = '" + optionRedeemPoint + '\'' + 
			"}";
		}
}