package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetRechargeDone {

    @SerializedName("RechargeOutput")
    private RechargeOutput rechargeOutput;

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("FailureRemarks")
    private String failureRemarks;

    public RechargeOutput getRechargeOutput() {
        return rechargeOutput;
    }

    public void setRechargeOutput(RechargeOutput rechargeOutput) {
        this.rechargeOutput = rechargeOutput;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    public String getFailureRemarks() {
        return failureRemarks;
    }

    public void setFailureRemarks(String failureRemarks) {
        this.failureRemarks = failureRemarks;
    }

    @Override
    public String toString() {
        return "ResponseGetRechargeDone{" +
                "rechargeOutput=" + rechargeOutput +
                ", responseStatus=" + responseStatus +
                ", userTrackId='" + userTrackId + '\'' +
                ", failureRemarks='" + failureRemarks + '\'' +
                '}';
    }
}