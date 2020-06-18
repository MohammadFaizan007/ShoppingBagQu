package com.shoppingbag.one_india_shopping.model.dashboard;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Banner{

	@SerializedName("banner")
	private List<BannerItem> banner;

	public void setBanner(List<BannerItem> banner){
		this.banner = banner;
	}

	public List<BannerItem> getBanner(){
		return banner;
	}
}