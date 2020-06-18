package com.shoppingbag.model.response.offer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAllOffer {

    @SerializedName("OfferImages")
    private List<OfferImagesItem> offerImages;

    @SerializedName("response")
    private String response;

    public List<OfferImagesItem> getOfferImages() {
        return offerImages;
    }

    public void setOfferImages(List<OfferImagesItem> offerImages) {
        this.offerImages = offerImages;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}