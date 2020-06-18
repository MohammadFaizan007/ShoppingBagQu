package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

public class TravelItem {

    @SerializedName("imageurl")
    private String imageurl;

    @SerializedName("name")
    private String name;

    @SerializedName("link")
    private String link;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}