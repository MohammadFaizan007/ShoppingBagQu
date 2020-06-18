package com.shoppingbag.one_india_shopping.model.dashboard;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DayDeal{

	@SerializedName("best_seller_item")
	private List<BestSellerItemItem> bestSellerItem;

	public void setBestSellerItem(List<BestSellerItemItem> bestSellerItem){
		this.bestSellerItem = bestSellerItem;
	}

	public List<BestSellerItemItem> getBestSellerItem(){
		return bestSellerItem;
	}

	@Override
 	public String toString(){
		return 
			"DayDeal{" + 
			"best_seller_item = '" + bestSellerItem + '\'' + 
			"}";
		}
}