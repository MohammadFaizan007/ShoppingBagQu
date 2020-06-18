package com.shoppingbag.model.response.m2p.addfund;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("result")
    private String result;

    @SerializedName("exception")
    private String exception;

    @SerializedName("pagination")
    private String pagination;

    @SerializedName("txId")
    private long txId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public long getTxId() {
        return txId;
    }

    public void setTxId(long txId) {
        this.txId = txId;
    }

    @Override
    public String toString() {
        return
                "Result{" +
                        "result = '" + result + '\'' +
                        ",exception = '" + exception + '\'' +
                        ",pagination = '" + pagination + '\'' +
                        ",txId = '" + txId + '\'' +
                        "}";
    }
}