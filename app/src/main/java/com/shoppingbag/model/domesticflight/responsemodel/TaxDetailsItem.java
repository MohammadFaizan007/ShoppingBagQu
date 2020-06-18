package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaxDetailsItem implements Serializable {

    @SerializedName("Description")
    private String description;

    @SerializedName("Amount")
    private float amount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return
                "TaxDetailsItem{" +
                        "description = '" + description + '\'' +
                        ",amount = '" + amount + '\'' +
                        "}";
    }
}