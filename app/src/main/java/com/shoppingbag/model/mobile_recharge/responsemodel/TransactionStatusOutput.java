package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class TransactionStatusOutput {

    @SerializedName("Remarks")
    private String remarks;

    @SerializedName("TransactionStatus")
    private int transactionStatus;

    @SerializedName("TransactionDetails")
    private TransactionDetails transactionDetails;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Override
    public String toString() {
        return
                "TransactionStatusOutput{" +
                        "remarks = '" + remarks + '\'' +
                        ",transactionStatus = '" + transactionStatus + '\'' +
                        ",transactionDetails = '" + transactionDetails + '\'' +
                        "}";
    }
}