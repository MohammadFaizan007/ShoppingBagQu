package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class TransactionDetails {

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
    private int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return
                "TransactionDetails{" +
                        "rechargeDateTime = '" + rechargeDateTime + '\'' +
                        ",operatorTransactionId = '" + operatorTransactionId + '\'' +
                        ",mobileNumber = '" + mobileNumber + '\'' +
                        ",operatorDescription = '" + operatorDescription + '\'' +
                        ",referenceNumber = '" + referenceNumber + '\'' +
                        ",amount = '" + amount + '\'' +
                        "}";
    }
}