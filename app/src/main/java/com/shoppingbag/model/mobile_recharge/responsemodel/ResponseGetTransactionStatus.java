package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetTransactionStatus {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("TransactionStatusOutput")
    private TransactionStatusOutput transactionStatusOutput;

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

    public TransactionStatusOutput getTransactionStatusOutput() {
        return transactionStatusOutput;
    }

    public void setTransactionStatusOutput(TransactionStatusOutput transactionStatusOutput) {
        this.transactionStatusOutput = transactionStatusOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseGetTransactionStatus{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",transactionStatusOutput = '" + transactionStatusOutput + '\'' +
                        "}";
    }
}