package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class PaymentDetails {

    @SerializedName("CurrencyCode")
    private String currencyCode;

    @SerializedName("Amount")
    private int amount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return
                "PaymentDetails{" +
                        "currencyCode = '" + currencyCode + '\'' +
                        ",amount = '" + amount + '\'' +
                        "}";
    }
}