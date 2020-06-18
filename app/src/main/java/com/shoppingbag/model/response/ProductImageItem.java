package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class ProductImageItem {

    @SerializedName("images")
    private String images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return
                "ProductImageItem{" +
                        "images = '" + images + '\'' +
                        "}";
    }
}