package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePaymentStatus {

    @SerializedName("response")
    private String response;

    @SerializedName("listPaymentTransactionDetails")
    private List<ListPaymentTransactionDetailsItem> listPaymentTransactionDetails;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ListPaymentTransactionDetailsItem> getListPaymentTransactionDetails() {
        return listPaymentTransactionDetails;
    }

    public void setListPaymentTransactionDetails(List<ListPaymentTransactionDetailsItem> listPaymentTransactionDetails) {
        this.listPaymentTransactionDetails = listPaymentTransactionDetails;
    }
}