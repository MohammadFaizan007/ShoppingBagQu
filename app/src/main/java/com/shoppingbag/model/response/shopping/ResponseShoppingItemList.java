package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseShoppingItemList {

    @SerializedName("response")
    private String response;

    @SerializedName("travel")
    private List<TravelItem> travel;

    @SerializedName("utilities")
    private List<TravelItem> utilities;

    @SerializedName("shopping")
    private List<TravelItem> shopping;

    @SerializedName("food")
    private List<TravelItem> foodList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<TravelItem> getTravel() {
        return travel;
    }

    public void setTravel(List<TravelItem> travel) {
        this.travel = travel;
    }

    public List<TravelItem> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<TravelItem> utilities) {
        this.utilities = utilities;
    }

    public List<TravelItem> getShopping() {
        return shopping;
    }

    public void setShopping(List<TravelItem> shopping) {
        this.shopping = shopping;
    }

    public List<TravelItem> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<TravelItem> foodList) {
        this.foodList = foodList;
    }
}