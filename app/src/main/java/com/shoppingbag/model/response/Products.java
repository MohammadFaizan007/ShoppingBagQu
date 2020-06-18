package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("features")
    private String features;

    @SerializedName("faQs")
    private String faQs;

    @SerializedName("productCode")
    private String productCode;

    @SerializedName("productImage")
    private String productImage;

    @SerializedName("productId")
    private String productId;

    @SerializedName("response")
    private Object response;

    @SerializedName("brandID")
    private String brandID;

    @SerializedName("availability")
    private String availability;

    @SerializedName("productName")
    private String productName;

    @SerializedName("productDescription")
    private String productDescription;

    @SerializedName("addedOn")
    private String addedOn;

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getFaQs() {
        return faQs;
    }

    public void setFaQs(String faQs) {
        this.faQs = faQs;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    @Override
    public String toString() {
        return
                "Products{" +
                        "features = '" + features + '\'' +
                        ",faQs = '" + faQs + '\'' +
                        ",productCode = '" + productCode + '\'' +
                        ",productImage = '" + productImage + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",response = '" + response + '\'' +
                        ",brandID = '" + brandID + '\'' +
                        ",availability = '" + availability + '\'' +
                        ",productName = '" + productName + '\'' +
                        ",productDescription = '" + productDescription + '\'' +
                        ",addedOn = '" + addedOn + '\'' +
                        "}";
    }
}