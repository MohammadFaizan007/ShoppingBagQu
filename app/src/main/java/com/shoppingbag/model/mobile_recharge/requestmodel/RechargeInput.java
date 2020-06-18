package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RechargeInput {

    @SerializedName("MobileNumber")
    private String mobileNumber;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("FK_MemID")
    private String fK_MemID;

    @SerializedName("OperatorCode")
    private String operatorCode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Override
    public String toString() {
        return "RechargeInput{" + "mobileNumber = '" + mobileNumber + '\'' + ",amount = '" + amount + '\'' + ",operatorCode = '" + operatorCode + '\'' + "}";
    }

    public String getfK_MemID() {
        return fK_MemID;
    }

    public void setfK_MemID(String fK_MemID) {
        this.fK_MemID = fK_MemID;
    }

}