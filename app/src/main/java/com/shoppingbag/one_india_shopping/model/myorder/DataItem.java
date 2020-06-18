package com.shoppingbag.one_india_shopping.model.myorder;


import com.google.gson.annotations.SerializedName;

public class DataItem {

    @SerializedName("shipping_amount")
    private String shippingAmount;

    @SerializedName("MONTH(s.created_at)")
    private String mONTHSCreatedAt;

    @SerializedName("subtotal")
    private String subtotal;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("sku")
    private String sku;

    @SerializedName("value")
    private String value;

    @SerializedName("status")
    private String status;

    public void setShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getShippingAmount() {
        return shippingAmount;
    }

    public void setMONTHSCreatedAt(String mONTHSCreatedAt) {
        this.mONTHSCreatedAt = mONTHSCreatedAt;
    }

    public String getMONTHSCreatedAt() {
        return mONTHSCreatedAt;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "shipping_amount = '" + shippingAmount + '\'' +
                        ",mONTH(s.created_at) = '" + mONTHSCreatedAt + '\'' +
                        ",subtotal = '" + subtotal + '\'' +
                        ",name = '" + name + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",entity_id = '" + entityId + '\'' +
                        ",sku = '" + sku + '\'' +
                        ",value = '" + value + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}