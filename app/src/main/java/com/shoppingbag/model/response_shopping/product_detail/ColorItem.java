package com.shoppingbag.model.response_shopping.product_detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ColorItem{

	@SerializedName("option_color")
	private List<OptionColorItem> optionColor;

	public void setOptionColor(List<OptionColorItem> optionColor){
		this.optionColor = optionColor;
	}

	public List<OptionColorItem> getOptionColor(){
		return optionColor;
	}

	@Override
 	public String toString(){
		return 
			"ColorItem{" + 
			"option_color = '" + optionColor + '\'' + 
			"}";
		}
}