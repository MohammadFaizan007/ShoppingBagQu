package com.shoppingbag.model.response.m2p;

import com.google.gson.annotations.SerializedName;

public class RequestAddFund {

    @SerializedName("transactionType")
    private String transactionType;

    @SerializedName("fromEntityId")
    private String fromEntityId;

    @SerializedName("amount")
    private double amount;

    @SerializedName("yapcode")
    private String yapcode;

    @SerializedName("productId")
    private String productId;

    @SerializedName("business")
    private String business;

    @SerializedName("businessEntityId")
    private String businessEntityId;

    @SerializedName("description")
    private String description;

    @SerializedName("toEntityId")
    private String toEntityId;

    @SerializedName("externalTransactionId")
    private String externalTransactionId;

    @SerializedName("transactionOrigin")
    private String transactionOrigin;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFromEntityId() {
        return fromEntityId;
    }

    public void setFromEntityId(String fromEntityId) {
        this.fromEntityId = fromEntityId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getYapcode() {
        return yapcode;
    }

    public void setYapcode(String yapcode) {
        this.yapcode = yapcode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBusinessEntityId() {
        return businessEntityId;
    }

    public void setBusinessEntityId(String businessEntityId) {
        this.businessEntityId = businessEntityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToEntityId() {
        return toEntityId;
    }

    public void setToEntityId(String toEntityId) {
        this.toEntityId = toEntityId;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public String getTransactionOrigin() {
        return transactionOrigin;
    }

    public void setTransactionOrigin(String transactionOrigin) {
        this.transactionOrigin = transactionOrigin;
    }
}