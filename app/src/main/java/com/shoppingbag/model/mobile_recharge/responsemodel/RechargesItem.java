package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class RechargesItem {

    @SerializedName("RechargeDateTime")
    private String rechargeDateTime;

    @SerializedName("OperatorTransactionId")
    private String operatorTransactionId;

    @SerializedName("MobileNumber")
    private String mobileNumber;

    @SerializedName("OperatorDescription")
    private String operatorDescription;

    @SerializedName("ReferenceNumber")
    private String referenceNumber;

    @SerializedName("Amount")
    private double amount;

    @SerializedName("RechargeStatus")
    private String rechargeStatus;

    public String getRechargeDateTime() {
        return rechargeDateTime;
    }

    public void setRechargeDateTime(String rechargeDateTime) {
        this.rechargeDateTime = rechargeDateTime;
    }

    public String getOperatorTransactionId() {
        return operatorTransactionId;
    }

    public void setOperatorTransactionId(String operatorTransactionId) {
        this.operatorTransactionId = operatorTransactionId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOperatorDescription() {
        return operatorDescription;
    }

    public void setOperatorDescription(String operatorDescription) {
        this.operatorDescription = operatorDescription;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(String rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    @Override
    public String toString() {
        return
                "RechargesItem{" +
                        "rechargeDateTime = '" + rechargeDateTime + '\'' +
                        ",operatorTransactionId = '" + operatorTransactionId + '\'' +
                        ",mobileNumber = '" + mobileNumber + '\'' +
                        ",operatorDescription = '" + operatorDescription + '\'' +
                        ",referenceNumber = '" + referenceNumber + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",rechargeStatus = '" + rechargeStatus + '\'' +
                        "}";
    }
}