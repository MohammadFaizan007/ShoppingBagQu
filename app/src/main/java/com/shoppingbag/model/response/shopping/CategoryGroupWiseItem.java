package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryGroupWiseItem implements Serializable {

    @SerializedName("images")
    private String images;

    @SerializedName("fK_CategoryId")
    private String fKCategoryId;

    @SerializedName("pK_CategoryId")
    private String pKCategoryId;

    @SerializedName("categoryName")
    private String categoryName;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getFKCategoryId() {
        return fKCategoryId;
    }

    public void setFKCategoryId(String fKCategoryId) {
        this.fKCategoryId = fKCategoryId;
    }

    public String getPKCategoryId() {
        return pKCategoryId;
    }

    public void setPKCategoryId(String pKCategoryId) {
        this.pKCategoryId = pKCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return
                "CategoryGroupWiseItem{" +
                        "images = '" + images + '\'' +
                        ",fK_CategoryId = '" + fKCategoryId + '\'' +
                        ",pK_CategoryId = '" + pKCategoryId + '\'' +
                        ",categoryName = '" + categoryName + '\'' +
                        "}";
    }
}