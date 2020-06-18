package com.shoppingbag.one_india_shopping.model.dashboard;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("best_deal")
	private List<BestDealItem> bestDeal;

	public void setBestDeal(List<BestDealItem> bestDeal){
		this.bestDeal = bestDeal;
	}

	public List<BestDealItem> getBestDeal(){
		return bestDeal;
	}

	@Override
 	public String toString(){
		return 
			"Category{" + 
			"best_deal = '" + bestDeal + '\'' + 
			"}";
		}
}