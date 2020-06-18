package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestSaveRecharge {

    @SerializedName("RechargeDateTime")
    private String rechargeDateTime;

    @SerializedName("OperatorTransactionId")
    private String operatorTransactionId;

    @SerializedName("RechargeBy")
    private String rechargeBy;

    @SerializedName("MobileNumber")
    private String mobileNumber;

    @SerializedName("OperatorDescription")
    private String operatorDescription;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("ReferenceNumber")
    private String referenceNumber;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("Fk_MemId")
    private String fk_MemId;

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

    public String getRechargeBy() {
        return rechargeBy;
    }

    public void setRechargeBy(String rechargeBy) {
        this.rechargeBy = rechargeBy;
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

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFk_MemId() {
        return fk_MemId;
    }

    public void setFk_MemId(String fk_MemId) {
        this.fk_MemId = fk_MemId;
    }

    @Override
    public String toString() {
        return
                "RequestSaveRecharge{" +
                        "rechargeDateTime = '" + rechargeDateTime + '\'' +
                        ",operatorTransactionId = '" + operatorTransactionId + '\'' +
                        ",rechargeBy = '" + rechargeBy + '\'' +
                        ",mobileNumber = '" + mobileNumber + '\'' +
                        ",operatorDescription = '" + operatorDescription + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",referenceNumber = '" + referenceNumber + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",fk_MemId = '" + fk_MemId + '\'' +
                        "}";
    }
}