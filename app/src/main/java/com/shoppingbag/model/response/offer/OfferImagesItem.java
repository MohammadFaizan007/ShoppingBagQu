package com.shoppingbag.model.response.offer;

import com.google.gson.annotations.SerializedName;

public class OfferImagesItem {

    @SerializedName("ImageName")
    private String imageName;

    @SerializedName("ImageURL")
    private String imageURL;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}