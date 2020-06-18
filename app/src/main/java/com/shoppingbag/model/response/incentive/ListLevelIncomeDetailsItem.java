package com.shoppingbag.model.response.incentive;

import com.google.gson.annotations.SerializedName;

public class ListLevelIncomeDetailsItem {

    @SerializedName("amount")
    private String amount;

    @SerializedName("utilityName")
    private String utilityName;

    @SerializedName("businessAmount")
    private String businessAmount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

    public String getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(String businessAmount) {
        this.businessAmount = businessAmount;
    }
}