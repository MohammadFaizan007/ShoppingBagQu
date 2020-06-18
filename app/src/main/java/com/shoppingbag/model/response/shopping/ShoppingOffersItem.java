package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

public class ShoppingOffersItem {

    @SerializedName("offerURL")
    private String offerURL;

    @SerializedName("offerOn")
    private String offerOn;

    @SerializedName("smallimages")
    private String smallimages;

    @SerializedName("imageURL")
    private String imageURL;

    @SerializedName("offerCategory")
    private String offerCategory;

    public String getOfferURL() {
        return offerURL;
    }

    public void setOfferURL(String offerURL) {
        this.offerURL = offerURL;
    }

    public String getOfferOn() {
        return offerOn;
    }

    public void setOfferOn(String offerOn) {
        this.offerOn = offerOn;
    }

    public String getSmallimages() {
        return smallimages;
    }

    public void setSmallimages(String smallimages) {
        this.smallimages = smallimages;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getOfferCategory() {
        return offerCategory;
    }

    public void setOfferCategory(String offerCategory) {
        this.offerCategory = offerCategory;
    }

    @Override
    public String toString() {
        return
                "ShoppingOffersItem{" +
                        "offerURL = '" + offerURL + '\'' +
                        ",offerOn = '" + offerOn + '\'' +
                        ",smallimages = '" + smallimages + '\'' +
                        ",imageURL = '" + imageURL + '\'' +
                        ",offerCategory = '" + offerCategory + '\'' +
                        "}";
    }
}