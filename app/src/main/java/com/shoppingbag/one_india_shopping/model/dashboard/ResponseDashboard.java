package com.shoppingbag.one_india_shopping.model.dashboard;

import com.google.gson.annotations.SerializedName;

public class ResponseDashboard {

    @SerializedName("cat")
    private Cat cat;

    @SerializedName("day_deal")
    private DayDeal dayDeal;

    @SerializedName("category")
    private Category category;


    @SerializedName("banner")
    private Banner banner;

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Cat getCat() {
        return cat;
    }

    public void setDayDeal(DayDeal dayDeal) {
        this.dayDeal = dayDeal;
    }

    public DayDeal getDayDeal() {
        return dayDeal;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return
                "ResponseDashboard{" +
                        "cat = '" + cat + '\'' +
                        ",day_deal = '" + dayDeal + '\'' +
                        ",category = '" + category + '\'' +
                        "}";
    }

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}
}