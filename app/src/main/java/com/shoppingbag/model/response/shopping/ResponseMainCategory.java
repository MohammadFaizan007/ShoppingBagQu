package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseMainCategory implements Serializable {

    @SerializedName("response")
    private String response;

    @SerializedName("categoryData")
    private List<CategoryDataItem> categoryData;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CategoryDataItem> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDataItem> categoryData) {
        this.categoryData = categoryData;
    }

    @Override
    public String toString() {
        return
                "ResponseMainCategory{" +
                        "response = '" + response + '\'' +
                        ",categoryData = '" + categoryData + '\'' +
                        "}";
    }
}