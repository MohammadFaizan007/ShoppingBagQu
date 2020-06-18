package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetRechargeHistory {

    @SerializedName("Recharges")
    private List<RechargesItem> recharges;

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("FailureRemarks")
    private String failureRemarks;

    public List<RechargesItem> getRecharges() {
        return recharges;
    }

    public void setRecharges(List<RechargesItem> recharges) {
        this.recharges = recharges;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }


    public String getFailureRemarks() {
        return failureRemarks;
    }

    public void setFailureRemarks(String failureRemarks) {
        this.failureRemarks = failureRemarks;
    }
}