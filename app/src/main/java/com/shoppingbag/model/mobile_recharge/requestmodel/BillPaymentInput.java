package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class BillPaymentInput {

    @SerializedName("MobileNumber")
    private String mobileNumber;

    @SerializedName("OtherDetails")
    private String otherDetails;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("FK_MemID")
    private String fKMemID;

    @SerializedName("OperatorCode")
    private String operatorCode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFKMemID() {
        return fKMemID;
    }

    public void setFKMemID(String fKMemID) {
        this.fKMemID = fKMemID;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Override
    public String toString() {
        return
                "BillPaymentInput{" +
                        "mobileNumber = '" + mobileNumber + '\'' +
                        ",otherDetails = '" + otherDetails + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",fK_MemID = '" + fKMemID + '\'' +
                        ",operatorCode = '" + operatorCode + '\'' +
                        "}";
    }
}