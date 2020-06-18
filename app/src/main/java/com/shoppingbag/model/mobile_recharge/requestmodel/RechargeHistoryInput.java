package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RechargeHistoryInput {

    @SerializedName("MobileNumber")
    private String mobileNumber;

    @SerializedName("FromDate")
    private String fromDate;

    @SerializedName("ToDate")
    private String toDate;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return
                "RechargeHistoryInput{" +
                        "mobileNumber = '" + mobileNumber + '\'' +
                        ",fromDate = '" + fromDate + '\'' +
                        ",toDate = '" + toDate + '\'' +
                        "}";
    }
}