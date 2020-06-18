package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class AccountStatementInput {

    @SerializedName("FromDate")
    private String fromDate;

    @SerializedName("ToDate")
    private String toDate;

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
                "AccountStatementInput{" +
                        "fromDate = '" + fromDate + '\'' +
                        ",toDate = '" + toDate + '\'' +
                        "}";
    }
}