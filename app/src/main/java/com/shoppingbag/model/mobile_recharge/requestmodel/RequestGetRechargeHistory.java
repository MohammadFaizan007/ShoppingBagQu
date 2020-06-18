package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetRechargeHistory {

    @SerializedName("RechargeHistoryInput")
    private RechargeHistoryInput rechargeHistoryInput;

    public RechargeHistoryInput getRechargeHistoryInput() {
        return rechargeHistoryInput;
    }

    public void setRechargeHistoryInput(RechargeHistoryInput rechargeHistoryInput) {
        this.rechargeHistoryInput = rechargeHistoryInput;
    }

    @Override
    public String toString() {
        return
                "RequestGetRechargeHistory{" +
                        "rechargeHistoryInput = '" + rechargeHistoryInput + '\'' +
                        "}";
    }
}