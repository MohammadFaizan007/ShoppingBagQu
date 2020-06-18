package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class AgentCreditBalanceOutput {

    @SerializedName("RemainingAmount")
    private double remainingAmount;

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    @Override
    public String toString() {
        return
                "AgentCreditBalanceOutput{" +
                        "remainingAmount = '" + remainingAmount + '\'' +
                        "}";
    }
}