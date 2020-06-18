package com.shoppingbag.model.response_shopping.product_detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SizeItem{

	@SerializedName("option_size")
	private List<OptionSizeItem> optionSize;

	public void setOptionSize(List<OptionSizeItem> optionSize){
		this.optionSize = optionSize;
	}

	public List<OptionSizeItem> getOptionSize(){
		return optionSize;
	}

	@Override
 	public String toString(){
		return 
			"SizeItem{" + 
			"option_size = '" + optionSize + '\'' + 
			"}";
		}
}