package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class HomePageItem {

    @SerializedName("distributionPrice")
    private String distributionPrice;

    @SerializedName("brandName")
    private String brandName;

    @SerializedName("offerName")
    private String offerName;

    @SerializedName("offerPrice")
    private String offerPrice;

    @SerializedName("productImage")
    private String productImage;

    @SerializedName("productId")
    private String productId;

    @SerializedName("brandId")
    private String brandId;

    @SerializedName("mrp")
    private String mrp;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("fk_CategoryId")
    private String fkCategoryId;

    public String getDistributionPrice() {
        return distributionPrice;
    }

    public void setDistributionPrice(String distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFkCategoryId() {
        return fkCategoryId;
    }

    public void setFkCategoryId(String fkCategoryId) {
        this.fkCategoryId = fkCategoryId;
    }
}